/******************************************************************************
 * Copyright (C) 2010-2016 CERN. All rights not expressly granted are reserved.
 * 
 * This file is part of the CERN Control and Monitoring Platform 'C2MON'.
 * C2MON is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the license.
 * 
 * C2MON is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with C2MON. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/
package cern.c2mon.web.ui.statistics.daqlog.charts;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.w3c.dom.Element;

import cern.c2mon.web.ui.statistics.daqlog.C2MONChartStyles;
import cern.c2mon.web.ui.statistics.daqlog.exceptions.GraphConfigException;
import cern.c2mon.web.ui.statistics.daqlog.exceptions.InvalidTableNameException;
import cern.c2mon.web.ui.statistics.daqlog.styles.PieChartStyle;
import cern.c2mon.web.ui.statistics.daqlog.values.IChartValue;
import cern.c2mon.web.ui.statistics.daqlog.values.PieChartValue;

public class JFreePieChart extends JFreeWebChart {

    /**
     * Title of the chart.
     */
    private String title;

    /**
     * Database table name where data is stored.
     */
    private String tableName;

    /**
     * Implementation of the configure abstract method for JFree pie charts.
     *
     * @param chartElement the JFreePieChart2D element
     * @param reference to the application chart styles
     * @throws GraphConfigException problem recognizing graph configuration parameters
     * @throws SQLException problem in getting data from database
     * @throws InvalidTableNameException the table name does not satisfy the required [a-zA-Z0-9_]+ format
     */
    @Override
    public void configure(final Element chartElement, final C2MONChartStyles C2MONChartStyles)
                        throws GraphConfigException, SQLException, InvalidTableNameException {

        logger.info("generating 2D pie chart with id " + chartId + " from XML");

        //set the pie chart specific attributes
        getParametersFromXML(chartElement);
        tableName = chartElement.getElementsByTagName("database-table").item(0).getFirstChild().getNodeValue();

        //if the table name is not alphanumeric (together with _), then reject
        if (!tableName.matches(new String("[a-zA-Z0-9_]+"))) {
            throw new InvalidTableNameException();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("retrieving pie chart data from database");
        }

        PieDataset dataset = getPieChartData(tableName);
        PieChartStyle pieChartStyle = C2MONChartStyles.getPieChartStyle();

        jFreeChart = createChart(dataset, pieChartStyle);

    }


    JFreeChart createChart(PieDataset dataset, PieChartStyle pieChartStyle) throws SQLException {
        if (logger.isDebugEnabled()) {
            logger.debug("entering createChart()...");
        }

        JFreeChart jFreeChart = ChartFactory.createPieChart(
                title,   //title
                dataset,  //dataset
                true,     //legend
                true,     //tooltips
                false);   //url

        //apply the generic pie chart style (modifies chart object!)
        pieChartStyle.applyTo(jFreeChart, dataset);
        if (logger.isDebugEnabled()) {
            logger.debug("...leaving createChart()");
        }
        return jFreeChart;
    }


    /**
     * Generates and sets the wrapped JFreeChart object when the chart is the member of a collection.
     */
    @Override
    public void configureMember(String memberName, WebChartCollection webChartCollection, Element chartElement, C2MONChartStyles C2MONChartStyles,
            List<IChartValue> valueList) throws GraphConfigException, SQLException {
            setGlobalParameters(memberName, webChartCollection);
            getParametersFromXML(memberName, chartElement);
            jFreeChart = createChart(toDataset(valueList), C2MONChartStyles.getPieChartStyle());

    }

    /**
     * Gets the PieChart specific parameters from the XML file and stores them in the object attributes.
     * @param chartElement the XML "chart" or "chart-group" element
     * @throws GraphConfigException problem in XML file
     */
    private void getParametersFromXML(Element chartElement) throws GraphConfigException {
        title = chartElement.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
    }

    /**
     * Gets the Pie Chart parameters from the XML document, inserts the member names, and sets the object attributes.
     * @param memberName
     * @param chartElement
     * @throws GraphConfigException
     */
    private void getParametersFromXML(String memberName, Element chartElement) throws GraphConfigException {
        getParametersFromXML(chartElement);
        subMemberName(memberName);
    }

    /**
     * Substitutes the member name for JFreePieChart-specific attributes.
     * @param memberName the name of the member chart
     */
    @Override
    public final void chartSubMemberName(final String memberName) {
        title = title.replace("$CHART_NAME$", memberName);
    }

    /**
     * Retrieves the graph data from the database for a pie chart.
     *
     * @param tableName the name of the table (view) containing the chart data
     * @return the dataset containing the data
     * @throws SQLException error in retrieving the data from the database
     */
    private PieDataset getPieChartData(final String tableName) throws SQLException {
        //to do: escape characters in table name here to avoid SQL injection!
        //retrieve the rows of the view as a list
        List chartValues = mapper.getPieChartData(tableName);
        return toDataset(chartValues);
    }

    /**
     * Converts a list of PieChartValues to a JFree DefaultPieDataset object.
     * @param valueList the list of values
     * @return the JFree DefaultPieDataset object
     */
    private static DefaultPieDataset toDataset(List<IChartValue> valueList) {
            DefaultPieDataset dataset = new DefaultPieDataset();
            Iterator<IChartValue> it = valueList.iterator();
            PieChartValue currentValue;

            //add the values to the dataset one by one
            while (it.hasNext()) {
                currentValue = (PieChartValue) it.next();
                dataset.setValue(currentValue.getKey(), currentValue.getValue());
            }
            return dataset;
    }

    /**
     * Retrieve the title of this chart.
     *
     * @return the chart title
     */
    public String getTitle() {
      return title;
    }
}
