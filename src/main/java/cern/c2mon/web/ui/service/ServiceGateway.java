///******************************************************************************
// * Copyright (C) 2010-2016 CERN. All rights not expressly granted are reserved.
// *
// * This file is part of the CERN Control and Monitoring Platform 'C2MON'.
// * C2MON is free software: you can redistribute it and/or modify it under the
// * terms of the GNU Lesser General Public License as published by the Free
// * Software Foundation, either version 3 of the license.
// *
// * C2MON is distributed in the hope that it will be useful, but WITHOUT ANY
// * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
// * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
// * more details.
// *
// * You should have received a copy of the GNU Lesser General Public License
// * along with C2MON. If not, see <http://www.gnu.org/licenses/>.
// *****************************************************************************/
//package cern.c2mon.web.ui.service;
//
//import javax.annotation.PostConstruct;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import cern.c2mon.client.core.C2monCommandManager;
//import cern.c2mon.client.core.C2monServiceGateway;
//import cern.c2mon.client.core.C2monTagManager;
//import cern.c2mon.client.ext.history.C2monHistoryManager;
//
///**
// * Service gateway wrapper for C2monServiceGateway
// */
//@Service
//public class ServiceGateway {
//
//  /**
//   * ServiceGateway logger
//   */
//  private static Logger logger = LoggerFactory.getLogger(TagService.class);
//
//  /**
//   * Tag manager
//   */
//  private C2monTagManager tagManager;
//
//  /**
//   * Command manager
//   */
//  private C2monCommandManager commandManager;
//
//  /**
//   * Command manager
//   */
//  private C2monHistoryManager historyManager;
//
//  /**
//   * Initializes the tagManager and commandManager
//   */
//  @PostConstruct
//  public void init() {
////    startC2monServiceGateway();
////    tagManager = C2monServiceGateway.getTagManager();
////    logger.info("TagManager instance started: " + (tagManager == null ? "NULL" : "OK"));
////    commandManager = C2monServiceGateway.getCommandManager();
////    logger.info("CommandManager instance started: " + (commandManager == null ? "NULL" : "OK"));
////    historyManager = C2monHistoryGateway.getHistoryManager();
////    logger.info("HistoryManager instance started: " + (historyManager == null ? "NULL" : "OK"));
//  }
//
//  /**
//   * Starts the C2monServiceGateway. No need to sleep!
//   *
//   **/
//  private void startC2monServiceGateway() {
//    logger.info("Starting C2MON Service Gateway...");
//    C2monServiceGateway.startC2monClientSynchronous();
//  }
//
//  /**
//   * Getter for tagManager
//   * @return tag manager instance
//   */
//  public C2monTagManager getTagManager() {
//    return this.tagManager;
//  }
//
//  /**
//   * Getter for commandManager
//   * @return command manager instance
//   */
//  public C2monCommandManager getCommandManager() {
//    return this.commandManager;
//  }
//
//  /**
//   * Getter for HistoryManager
//   * @return History Manager instance
//   */
//  public C2monHistoryManager getHistoryManager() {
//    return historyManager;
//  }
//
//}
