package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Device;

public interface DeviceDAO {

	Device get(String id);

	Device getByUser(Integer userId);

	List<Device> getAll();

	void save(Device device);

	boolean updateRegistrationId(Device device);

	boolean updateUserId(Device device);
}
