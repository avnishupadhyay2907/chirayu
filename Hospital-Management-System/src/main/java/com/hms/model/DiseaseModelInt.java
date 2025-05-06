package com.hms.model;

import java.util.List;

import com.hms.dto.DiseaseDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;

public interface DiseaseModelInt {

	public long add(DiseaseDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(DiseaseDTO dto) throws ApplicationException;

	public void update(DiseaseDTO dto) throws ApplicationException, DuplicateRecordException;

	public DiseaseDTO findByPK(long pk) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(DiseaseDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public List search(DiseaseDTO dto) throws ApplicationException;

}
