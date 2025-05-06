package com.hms.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.hms.dto.DoctorScheduleDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.util.HibDataSource;

public class DoctorScheduleModelHibImp implements DoctorScheduleModelInt {

	public long add(DoctorScheduleDTO dto) throws ApplicationException, DuplicateRecordException {
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
			throw new ApplicationException("Exception in Doctor Schedule Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(DoctorScheduleDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Doctor Schedule Delete " + e.getMessage());
		} finally {
			session.close();
		}

	}

	public void update(DoctorScheduleDTO dto) throws ApplicationException, DuplicateRecordException {
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
			throw new ApplicationException("Exception in Doctor Schedule update" + e.getMessage());
		} finally {
			session.close();
		}

	}

	public DoctorScheduleDTO findByPK(long pk) throws ApplicationException {
		System.out.println("PK =====>" + pk + "<=====PK");
		Session session = null;
		DoctorScheduleDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (DoctorScheduleDTO) session.get(DoctorScheduleDTO.class, pk);
			System.out.println(dto);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting DoctorScheduleDTO by pk");
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
			Criteria criteria = session.createCriteria(DoctorScheduleDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  Doctor Schedule list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(DoctorScheduleDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DoctorScheduleDTO.class);
			if (dto != null) {

				/*
				 * if (dto.getId() != 0 && dto.getId() > 0) { criteria.add(Restrictions.eq("id",
				 * dto.getId())); }
				 */
				if (dto.getDay() != null && dto.getDay().length() > 0) {
					criteria.add(Restrictions.like("day", dto.getDay() + "%"));
				}

				if (dto.getStartTime() != null && dto.getStartTime().length() > 0) {
					criteria.add(Restrictions.like("startTime", dto.getStartTime() + "%"));
				}
				if (dto.getEndTime() != null && dto.getEndTime().length() > 0) {
					criteria.add(Restrictions.like("endTime", dto.getEndTime() + "%"));
				}
				if (dto.getDoctorId() != 0 && dto.getDoctorId() > 0) {
					criteria.add(Restrictions.eq("doctorId", dto.getDoctorId()));
				}

			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Doctor Schedule search");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(DoctorScheduleDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

}
