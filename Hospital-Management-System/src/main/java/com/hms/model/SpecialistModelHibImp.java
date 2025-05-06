package com.hms.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.hms.dto.SpecialistDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.util.HibDataSource;

public class SpecialistModelHibImp implements SpecialistModelInt {

	public long add(SpecialistDTO dto) throws ApplicationException, DuplicateRecordException {
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
			throw new ApplicationException("Exception in Specialist Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(SpecialistDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Specialist Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(SpecialistDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;

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
			throw new ApplicationException("Exception in Specialist update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public SpecialistDTO findByPK(long pk) throws ApplicationException {
		System.out.println("PK =====>" + pk + "<=====PK");
		Session session = null;
		SpecialistDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (SpecialistDTO) session.get(SpecialistDTO.class, pk);
			System.out.println(dto);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting SpecialistDTO by pk");
		} finally {
			session.close();
		}
		System.out.println("DTO ===>" + dto);
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
			Criteria criteria = session.createCriteria(SpecialistDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  Specialist list");
		} finally {
			session.close();
		}

		return list;

	}

	public List search(SpecialistDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SpecialistDTO.class);
			if (dto != null) {

				/*
				 * if (dto.getId() != 0 && dto.getId() > 0) { criteria.add(Restrictions.eq("id",
				 * dto.getId())); }
				 */
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Specialist search");
		} finally {
			session.close();
		}
		return list;

	}

	public List search(SpecialistDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

}
