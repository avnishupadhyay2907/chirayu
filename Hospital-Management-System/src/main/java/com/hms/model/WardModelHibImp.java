package com.hms.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.hms.dto.DoctorDTO;
import com.hms.dto.WardDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.util.HibDataSource;

public class WardModelHibImp implements WardModelInt {

	public long add(WardDTO dto) throws ApplicationException, DuplicateRecordException {

		System.out.println("===> WARD ADD <===");
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
			throw new ApplicationException("Exception in Ward Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(WardDTO dto) throws ApplicationException {

		System.out.println("===> WARD DELETE <===");

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
			throw new ApplicationException("Exception in Ward Delete " + e.getMessage());
		} finally {
			session.close();
		}

	}

	public void update(WardDTO dto) throws ApplicationException, DuplicateRecordException {

		System.out.println("===> WARD UPDATE <===");

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
			throw new ApplicationException("Exception in Ward update" + e.getMessage());
		} finally {
			session.close();
		}

	}

	public WardDTO findByPK(long pk) throws ApplicationException {
		System.out.println("PK =====>" + pk + "<=====PK");
		Session session = null;
		WardDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (WardDTO) session.get(WardDTO.class, pk);
			System.out.println(dto);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting WardDTO by pk");
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
			Criteria criteria = session.createCriteria(WardDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  Ward list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(WardDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(WardDTO.class);
			if (dto != null) {

				/*
				 * if (dto.getId() != 0 && dto.getId() > 0) { criteria.add(Restrictions.eq("id",
				 * dto.getId())); }
				 */
				if (dto.getType() != null && dto.getType().length() > 0) {
					criteria.add(Restrictions.like("type", dto.getType() + "%"));
				}

				if (dto.getCapacity() != null && dto.getCapacity().length() > 0) {
					criteria.add(Restrictions.like("capacity", dto.getCapacity() + "%"));
				}
				if (dto.getChargesPerDay() != null && dto.getChargesPerDay().length() > 0) {
					criteria.add(Restrictions.like("chargesPerDay", dto.getChargesPerDay() + "%"));
				}

			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Ward search");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(WardDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

}
