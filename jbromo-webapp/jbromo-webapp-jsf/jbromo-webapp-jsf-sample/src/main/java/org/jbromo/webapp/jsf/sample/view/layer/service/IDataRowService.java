package org.jbromo.webapp.jsf.sample.view.layer.service;

import javax.ejb.Local;

import org.jbromo.service.crud.memory.IMemoryService;

/**
 * Define the DataRow service interface.
 * 
 * @author qjafcunuas
 * 
 */
@Local
public interface IDataRowService extends IMemoryService<DataRow> {

}
