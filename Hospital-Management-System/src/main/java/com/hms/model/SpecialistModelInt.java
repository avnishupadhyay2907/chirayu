package com.hms.model;

import java.util.List;

import com.hms.dto.SpecialistDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;

public interface SpecialistModelInt {

	public long add(SpecialistDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(SpecialistDTO dto) throws ApplicationException;

	public void update(SpecialistDTO dto) throws ApplicationException, DuplicateRecordException;

	public SpecialistDTO findByPK(long pk) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(SpecialistDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public List search(SpecialistDTO dto) throws ApplicationException;
	
	
}
