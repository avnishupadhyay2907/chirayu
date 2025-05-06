package com.hms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.hms.dto.UserDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.exception.RecordNotFoundException;
import com.hms.util.EmailBuilder;
import com.hms.util.EmailMessage;
import com.hms.util.EmailUtility;
import com.hms.util.HibDataSource;

public class UserModelHibImp implements UserModelInt {

	public long add(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		System.out.println("===> in add");
		// TODO Auto-generated method stub
		/* log.debug("usermodel hib start"); */

		UserDTO existDto = null;
		existDto = findByUserName(dto.getUserName());
		if (existDto != null) {
			throw new DuplicateRecordException("User Name already exist");
		}
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			int pk = 0;
			tx = session.beginTransaction();

			System.out.println("trac1");
			session.save(dto);
			System.out.println("trac2");
			tx.commit();
			System.out.println("trac3");
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in User Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();
	}

	public void delete(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		UserDTO existDto = findByUserName(dto.getUserName());
		// Check if updated LoginId already exist
		if (existDto != null && existDto.getId() != dto.getId()) {

			System.out.println("in duplicate reocord  condition model update");
			throw new DuplicateRecordException("LoginId is already exist");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public UserDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (UserDTO) session.get(UserDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public UserDTO findByUserName(String userName) throws ApplicationException {
	    Session session = null;
	    UserDTO dto = null;

	    try {
	        session = HibDataSource.getSession();

	        Criteria criteria = session.createCriteria(UserDTO.class);
	        criteria.add(Restrictions.eq("userName", userName));

	        List list = criteria.list();
	        if (list != null && list.size() == 1) {
	            dto = (UserDTO) list.get(0);
	        }
	    } catch (HibernateException e) {
	        e.printStackTrace();
	        throw new ApplicationException("Exception in getting User by Login: " + e.getMessage());
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }

	    return dto;
	}


	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Users list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub

		System.out.println(
				"hellllo" + pageNo + "....." + pageSize + "........" + dto.getId() + "......" + dto.getRoleId());
		Session session = null;
		ArrayList<UserDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (dto != null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.like("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}

				if (dto.getUserName() != null && dto.getUserName().length() > 0) {
					criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
				}
				if (dto.getPassword() != null && dto.getPassword().length() > 0) {
					criteria.add(Restrictions.like("password", dto.getPassword() + "%"));
				}
				if (dto.getGender() != null && dto.getGender().length() > 0) {
					criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
				}
				if (dto.getDob() != null && dto.getDob().getTime() > 0) {
					criteria.add(Restrictions.eq("dob", dto.getDob()));
				}
				if (dto.getRoleId() > 0) {
					criteria.add(Restrictions.eq("roleId", dto.getRoleId()));
				}

			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<UserDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in user search");
		} finally {
			session.close();
		}

		return list;
	}

	public UserDTO authenticate(String login, String password) throws ApplicationException {
		// TODO Auto-generated method stub
		System.out.println(login + "kkkkk" + password);
		Session session = null;
		UserDTO dto = null;
		session = HibDataSource.getSession();
		Query q = session.createQuery("from UserDTO where userName=? and password=?");
		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (UserDTO) list.get(0);
		} else {
			dto = null;

		}
		return dto;
	}

	public List getRoles(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean changePassword(long id, String newPassword, String oldPassword)
			throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		boolean flag = false;
		UserDTO dtoExist = null;

		dtoExist = findByPK(id);
		System.out.println("in method password" + dtoExist.getPassword() + "jjjjjjj" + oldPassword);
		if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {
			dtoExist.setPassword(newPassword);
			try {
				update(dtoExist);
			} catch (DuplicateRecordException e) {

				throw new ApplicationException("UserName is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("UserName not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("userName", dtoExist.getUserName());
		map.put("password", dtoExist.getPassword());
		map.put("name", dtoExist.getName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dtoExist.getUserName());
		msg.setSubject("Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return flag;

	}

	public boolean forgetPassword(String userName) throws RecordNotFoundException, ApplicationException {
		// TODO Auto-generated method stub
		UserDTO userData = findByUserName(userName);
		boolean flag = false;
		if (userData == null) {
			System.out.println("email id does not exist");
			throw new RecordNotFoundException("Email Id Does not matched.");
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getName());

		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(userName);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		System.out.println(flag);
		flag = true;

		return flag;
	}

	public boolean resetPassword(UserDTO dto) throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
		UserDTO userData = findByPK(dto.getId());
		userData.setPassword(newPassword);

		try {
			update(userData);
		} catch (DuplicateRecordException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userName", dto.getUserName());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getUserName());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return true;
	}

	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		long pk = add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userName", dto.getUserName());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getUserName());
		msg.setSubject("Registration is successful for ORS Project SUNRAYS Technologies");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}

}
