package com.hms.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.hms.dto.DoctorDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.util.HibDataSource;

public class DoctorModelHibImp implements DoctorModelInt {

	public long add(DoctorDTO dto) throws ApplicationException, DuplicateRecordException {
	    if (dto == null) {
	        throw new ApplicationException("DoctorDTO is null in add() method.");
	    }

	    Session session = null;
	    Transaction tx = null;

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
	        throw new ApplicationException("Exception in Doctor Add " + e.getMessage());
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }

	    return dto.getId();  // safe now
	}


	public void delete(DoctorDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Doctor Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(DoctorDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		DoctorDTO existDto = findByEmail(dto.getEmail());
		// Check if updated LoginId already exist
		if (existDto != null && existDto.getId() != dto.getId()) {

			System.out.println("in duplicate reocord  condition model update");
			throw new DuplicateRecordException("Email is already exist");
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
			throw new ApplicationException("Exception in Doctor update" + e.getMessage());
		} finally {
			session.close();
		}

	}

	public DoctorDTO findByPK(long pk) throws ApplicationException {
		System.out.println("PK =====>" + pk + "<=====PK");
		Session session = null;
		DoctorDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (DoctorDTO) session.get(DoctorDTO.class, pk);
			System.out.println(dto);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting DoctorDTO by pk");
		} finally {
			session.close();
		}
		System.out.println("DTO ===>" + dto);
		return dto;
	}

	public DoctorDTO findByEmail(String email) throws ApplicationException {
		Session session = null;
		DoctorDTO dto = null;

		if (email == null) {
			throw new ApplicationException("Email cannot be null.");
		}

		try {
			session = HibDataSource.getSession();
			if (session == null) {
				throw new ApplicationException("Hibernate session is null.");
			}

			Criteria criteria = session.createCriteria(DoctorDTO.class);
			criteria.add(Restrictions.eq("email", email));
			List list = criteria.list();

			if (list != null && list.size() == 1) {
				dto = (DoctorDTO) list.get(0);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Doctor by email " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return dto;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DoctorDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  Doctor list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(DoctorDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DoctorDTO.class);
			if (dto != null) {

				/*
				 * if (dto.getId() != 0 && dto.getId() > 0) { criteria.add(Restrictions.eq("id",
				 * dto.getId())); }
				 */
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}

				if (dto.getPhone() != null && dto.getPhone().length() > 0) {
					criteria.add(Restrictions.like("phone", dto.getPhone() + "%"));
				}
				if (dto.getEmail() != null && dto.getEmail().length() > 0) {
					criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
				}

			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Doctor search");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(DoctorDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

}
