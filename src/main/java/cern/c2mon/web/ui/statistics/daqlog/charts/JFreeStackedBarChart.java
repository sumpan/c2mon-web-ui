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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.w3c.dom.Element;

import cern.c2mon.web.ui.statistics.daqlog.C2MONChartStyles;
import cern.c2mon.web.ui.statistics.daqlog.exceptions.GraphConfigException;
import cern.c2mon.web.ui.statistics.daqlog.exceptions.InvalidTableNameException;
import cern.c2mon.web.ui.statistics.daqlog.styles.BarChartStyle;
import cern.c2mon.web.ui.statistics.daqlog.values.IChartValue;
import cern.c2mon.web.ui.statistics.daqlog.values.StackedBarChartValue;

public class JFreeStackedBarChart extends JFreeBarChart {

    @Override
    public void configure(Element chartElement, C2MONChartStyles C2MONChartStyles) throws SQLException, GraphConfigException, InvalidTableNameException {
        if (logger.isDebugEnabled()) {
            logger.debug("retrieving stacked bar chart data from database");
        }

        getParametersFromXML(chartElement);

        //get the dataset and the group mapping for the stacked bar chart
        HashMap datasetHash = getStackedBarChartData(tableName);

        //create the stacked bar chart
        jFreeChart = createChart((Object) datasetHash, C2MONChartStyles.getBarChartStyle());
    }

    /**
     * Configures the JFreeBarChart when the chart is the member of a collection.
     * @throws InvalidTableNameException
     */
//    public void configureMember(String memberName, Element chartElement, C2MONChartStyles C2MONChartStyles,
//            List<IChartValue> valueList) throws GraphConfigException, SQLException, InvalidTableNameException {
//            setGlobalParameters(memberName, webChartCollection);
//            //get parameters from XML file and substitute member name
//            getParametersFromXML(memberName, chartElement);
//            jFreeChart = createChart(toDataset(valueList), C2MONChartStyles.getBarChartStyle());
//
//    }
//

    private HashMap getStackedBarChartData(final String tableName) throws SQLException {
        //retrieve the chart values from the database
        List chartValues = mapper.getStackedBarChartData(tableName);
        return (HashMap) toDataset(chartValues);

    }

    @Override
    protected Object toDataset(List<IChartValue> valueList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Iterator<IChartValue> it = valueList.iterator();
        StackedBarChartValue currentValue;
        HashMap<String, String> groupMapping = new HashMap<String, String>();
        //add the values to the dataset one by one, and to the groupMapping
        while (it.hasNext()) {
            currentValue = (StackedBarChartValue) it.next();
            dataset.addValue(currentValue.getValue(), currentValue.getSeriesKey(), currentValue.getCategoryKey());
            if (!groupMapping.containsKey(currentValue.getSeriesKey())) {
                groupMapping.put(currentValue.getSeriesKey(), currentValue.getGroup());
            }
        }
        HashMap datasetHashmap = new HashMap();
        datasetHashmap.put("dataset", dataset);
        datasetHashmap.put("groupMapping", groupMapping);
        return (Object) datasetHashmap;
    }


    @Override
    protected JFreeChart createChart(Object datasetHashObject, BarChartStyle barChartStyle) throws SQLException {
        if (logger.isDebugEnabled()) {
            logger.debug("entering createChart()...");
        }
        HashMap datasetHash = (HashMap) datasetHashObject;

        //create the bar chart
        JFreeChart jFreeChart = ChartFactory.createStackedBarChart(
                title,                                                  // chart title
                domainAxis,                                             // domain axis label
                rangeAxis,                                              // range axis label
                (DefaultCategoryDataset) datasetHash.get("dataset"),    // data
                plotOrientation,                                        // orientation
                true,                                                   // include legend
                true,                                                   // tooltips?
                false                                                   // URLs?
            );

        //apply the TIM bar chart style to the chart
        barChartStyle.applyTo(jFreeChart, (DefaultCategoryDataset) datasetHash.get("dataset"), (HashMap) datasetHash.get("groupMapping"));
        if (logger.isDebugEnabled()) {
            logger.debug("...leaving createChart()");
        }
        return jFreeChart;
    }
}
