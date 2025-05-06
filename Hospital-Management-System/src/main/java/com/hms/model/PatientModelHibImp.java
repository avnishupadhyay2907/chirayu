package com.hms.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.hms.dto.PatientDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.util.HibDataSource;

public class PatientModelHibImp implements PatientModelInt {

	public long add(PatientDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		PatientDTO duplicatePhoneNo = findByPhoneNo(dto.getPhone());
		if (duplicatePhoneNo != null) {
			throw new DuplicateRecordException("Phone No already exist");
		}
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Patient Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(PatientDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Patient Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(PatientDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		PatientDTO existDto = findByPhoneNo(dto.getPhone());
		// Check if updated LoginId already exist
		if (existDto != null && existDto.getId() != dto.getId()) {

			System.out.println("in duplicate reocord  condition model update");
			throw new DuplicateRecordException("Phone No is already exist");
		}
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			System.out.println("before update");

			session.saveOrUpdate(dto);
			System.out.println("after update");
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Patient update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public PatientDTO findByPK(long pk) throws ApplicationException {
		System.out.println("======" + pk + "=======");
		Session session = null;
		PatientDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (PatientDTO) session.get(PatientDTO.class, pk);
			System.out.println(dto);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting PatientDTO by pk");
		} finally {
			session.close();
		}
		System.out.println("++++" + dto);
		return dto;
	}

	public PatientDTO findByPhoneNo(String phone) throws ApplicationException {
		Session session = null;
		PatientDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PatientDTO.class);
			criteria.add(Restrictions.eq("phone", phone));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (PatientDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Patient by Phone " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;

	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);

	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PatientDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  Patient list");
		} finally {
			session.close();
		}

		return list;

	}

	public List search(PatientDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PatientDTO.class);
			if (dto != null) {

				if (dto.getId() != 0 && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				if (dto.getAge() != null && dto.getAge().getTime() > 0) {
					criteria.add(Restrictions.eq("age", dto.getAge()));
				}
				if (dto.getGender() != null && dto.getGender().length() > 0) {
					criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
				}
				if (dto.getPhone() != null && dto.getPhone().length() > 0) {
					criteria.add(Restrictions.like("phone", dto.getPhone() + "%"));
				}
				if (dto.getAddress() != null && dto.getAddress().length() > 0) {
					criteria.add(Restrictions.like("address", dto.getAddress() + "%"));
				}
				if (dto.getWardId() != 0 && dto.getWardId() > 0) {
					criteria.add(Restrictions.eq("wardId", dto.getWardId()));
				}
				if (dto.getDoctorId() != 0 && dto.getDoctorId() > 0) {
					criteria.add(Restrictions.eq("doctorId", dto.getDoctorId()));
				}
				if (dto.getDiseaseId() != 0 && dto.getDiseaseId() > 0) {
					criteria.add(Restrictions.eq("diseaseId", dto.getDiseaseId()));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Patient search");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(PatientDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

}
