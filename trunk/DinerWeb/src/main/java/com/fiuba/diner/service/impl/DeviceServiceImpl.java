package com.fiuba.diner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.DeviceDAO;
import com.fiuba.diner.model.Device;
import com.fiuba.diner.service.DeviceService;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

	private static final Logger logger = Logger.getLogger(DeviceServiceImpl.class);

	@Autowired
	private DeviceDAO deviceDAO;

	@Override
	public Device get(String id) {
		return this.deviceDAO.get(id);
	}

	@Override
	public List<Device> getAll() {
		return this.deviceDAO.getAll();
	}

	@Override
	public void save(Device device) {
		this.deviceDAO.save(device);
		logger.info("Se han guardado los cambios exitosamente. Id de Device: " + device.getId());
	}

	@Override
	public boolean updateRegistrationId(Device device) {
		logger.info("Se han guardado los cambios exitosamente. Id de Device: " + device.getId());
		return this.deviceDAO.updateRegistrationId(device);
	}

}
