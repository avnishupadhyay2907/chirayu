package com.hms.model;

import java.util.List;

import com.hms.dto.DoctorScheduleDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;

public interface DoctorScheduleModelInt {

	public long add(DoctorScheduleDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(DoctorScheduleDTO dto) throws ApplicationException;

	public void update(DoctorScheduleDTO dto) throws ApplicationException, DuplicateRecordException;

	public DoctorScheduleDTO findByPK(long pk) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(DoctorScheduleDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public List search(DoctorScheduleDTO dto) throws ApplicationException;

}
