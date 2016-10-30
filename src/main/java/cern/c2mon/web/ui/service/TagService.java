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
package cern.c2mon.web.ui.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import cern.c2mon.client.core.C2monTagManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cern.c2mon.client.common.tag.ClientDataTagValue;
import cern.c2mon.client.core.tag.ClientDataTagImpl;
import cern.c2mon.shared.client.tag.TagConfig;
import cern.c2mon.shared.client.tag.TagConfigImpl;

/**
 * Datatag service providing the XML representation of a given datatag
 */
@Service
public class TagService {

  /**
   * TagService logger
   */
  private static Logger logger = LoggerFactory.getLogger(TagService.class);

  /**
   * Gateway to C2monService
   */
  @Autowired
//  private ServiceGateway gateway;

  private C2monTagManager tagManager;

  /**
   * Gets the XML representation of the current value of datatag
   * @param dataTagId id of the datatag
   * @return XML datatag value representation
   * @throws TagIdException if the datatag was not found or a non-numeric id was requested ({@link TagIdException}), or any other exception
   * thrown by the underlying service gateway.
   */
  public String getDataTagValueXml(final String dataTagId) throws TagIdException {
    try {
      ClientDataTagImpl value = (ClientDataTagImpl) getDataTagValue(Long.parseLong(dataTagId));
      if (value != null)
        return value.getXml();
      else
        throw new TagIdException("No datatag found");
    } catch (NumberFormatException e) {
      throw new TagIdException("Invalid datatag id");
    }
  }

  /**
   * Gets the XML representation of the datatag configuration
   * @param tagId id of the datatag
   * @return XML datatag config representation
   * @throws TagIdException if the datatag was not found or a non-numeric id was requested ({@link TagIdException}), or any other exception
   * thrown by the underlying service gateway.
   */

  public String getDataTagConfigXml(final String tagId) throws TagIdException {
    try {
      TagConfigImpl config = (TagConfigImpl) getTagConfig(Long.parseLong(tagId));
      if (config != null)
        return config.getXml();
      else
        throw new TagIdException("No datatag found");
    } catch (NumberFormatException e) {
      throw new TagIdException("Invalid datatag id");
    }
  }

  /**
   * Retrieves a tagConfig object from the service gateway tagManager
   * @param tagId id of the datatag
   * @return tag configuration
   */
  public TagConfig getTagConfig(final long tagId) {
    TagConfig tc = null;
    List<Long> tagIds = new ArrayList<Long>();
    tagIds.add(tagId);
    Collection<TagConfig> tagConfigs = tagManager.getTagConfigurations(tagIds);
    Iterator<TagConfig> it = tagConfigs.iterator();
    if (it.hasNext()) {
      tc = it.next();
    }
    logger.debug("Tag config fetch for tag " + tagId + ": " + (tc == null ? "NULL" : "SUCCESS"));
    return tc;
  }

  /**
   * Retrieves a tagValue object from the service gateway tagManager
   * @param dataTagId id of the datatag
   * @return tag value
   */
  public ClientDataTagValue getDataTagValue(final long dataTagId) {
    ClientDataTagValue dt = null;
    List<Long> tagIds = new ArrayList<Long>();
    tagIds.add(dataTagId);
    Collection<ClientDataTagValue> dataTags = tagManager.getDataTags(tagIds);
    Iterator<ClientDataTagValue> it = dataTags.iterator();
    if (it.hasNext()) {
      dt = it.next();
    }
    logger.debug("Datatag value fetch for tag " + dataTagId + ": " + (dt == null ? "NULL" : "SUCCESS"));
    return dt;
  }
}


