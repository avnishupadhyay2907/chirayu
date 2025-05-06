package com.hms.model;

import java.util.List;

import com.hms.dto.WardDTO;
import com.hms.dto.WardDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;

public interface WardModelInt {

	public long add(WardDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(WardDTO dto) throws ApplicationException;

	public void update(WardDTO dto) throws ApplicationException, DuplicateRecordException;

	public WardDTO findByPK(long pk) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(WardDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public List search(WardDTO dto) throws ApplicationException;

}
