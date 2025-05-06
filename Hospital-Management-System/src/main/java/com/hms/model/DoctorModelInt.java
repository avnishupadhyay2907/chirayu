package com.hms.model;

import java.util.List;

import com.hms.dto.DoctorDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;

public interface DoctorModelInt {

	public long add(DoctorDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(DoctorDTO dto) throws ApplicationException;

	public void update(DoctorDTO dto) throws ApplicationException, DuplicateRecordException;

	public DoctorDTO findByPK(long pk) throws ApplicationException;

	public DoctorDTO findByEmail(String email) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(DoctorDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public List search(DoctorDTO dto) throws ApplicationException;

}
