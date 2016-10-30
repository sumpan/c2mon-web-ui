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
package cern.c2mon.web.ui.statistics.daqlog;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cern.c2mon.web.ui.statistics.daqlog.values.BarChartValue;
import cern.c2mon.web.ui.statistics.daqlog.values.IChartCollectionValue;
import cern.c2mon.web.ui.statistics.daqlog.values.PieChartValue;
import cern.c2mon.web.ui.statistics.daqlog.values.StackedBarChartValue;

/**
 * This interface replaces the old iBatis mapper
 * (cern.c2mon.web.ui.statistics.SqlMapper) with a newer MyBatis
 * version.
 *
 * @author Justin Lewis Salmon
 */
public interface DAQLogStatisticsMapper {

  /**
   * Method returning a List of BarChartValue's read from the database.
   *
   * @param tableName the table/view from which the values must be read
   * @return the list of values
   * @throws SQLException error in database transaction
   */
  public List<BarChartValue> getBarChartData(@Param("tableName") final String tableName);

  public List<IChartCollectionValue> getBarChartCollectionData(@Param("tableName") final String tableName);

  /**
   * The static method used for collecting the stacked bar chart data from the
   * database.
   *
   * @param tableName the table/view from which the values are read
   * @return the list of values
   * @throws SQLException exception in database transaction
   */
  public List<StackedBarChartValue> getStackedBarChartData(@Param("tableName") final String tableName);

  /**
   * Static method returning getting the data from the database for collections
   * of stacked bar charts.
   *
   * @param tableName the table where the values are kept
   * @return a list of values
   * @throws SQLException if problem with database transaction
   */
  public List<IChartCollectionValue> getStackedBarChartCollectionData(@Param("tableName") final String tableName);

  /**
   * Method returning a List of PieChartValue's read from the database.
   *
   * @param tableName the table/view from which the values must be read
   * @return the list of values
   * @throws SQLException error in database transaction
   */
  public List<PieChartValue> getPieChartData(@Param("tableName") final String tableName);

  public List<IChartCollectionValue> getPieChartCollectionData(@Param("tableName") final String tableName);
}
