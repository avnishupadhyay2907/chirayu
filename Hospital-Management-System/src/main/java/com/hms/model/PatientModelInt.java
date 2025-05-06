package com.hms.model;

import java.util.List;

import com.hms.dto.PatientDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;

public interface PatientModelInt {

	public long add(PatientDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(PatientDTO dto) throws ApplicationException;

	public void update(PatientDTO dto) throws ApplicationException, DuplicateRecordException;

	public PatientDTO findByPK(long pk) throws ApplicationException;

	public PatientDTO findByPhoneNo(String phone) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(PatientDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public List search(PatientDTO dto) throws ApplicationException;
	
}
