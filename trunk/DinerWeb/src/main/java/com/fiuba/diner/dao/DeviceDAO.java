package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Device;

public interface DeviceDAO {

	Device get(String id);

	List<Device> getAll();

	void save(Device device);

	boolean updateRegistrationId(Device device);
}
