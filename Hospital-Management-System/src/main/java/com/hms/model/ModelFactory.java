package com.hms.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * ModelFactory decides which model implementation run
 * 
 * @author Avnish Upadhyay
 *
 */
public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("com.hms.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	private ModelFactory() {

	}

	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	public UserModelInt getUserModel() {

		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelHibImp();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}

	public DiseaseModelInt getDiseaseModel() {

		DiseaseModelInt diseaseModel = (DiseaseModelInt) modelCache.get("diseaseModel");
		if (diseaseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				diseaseModel = new DiseaseModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				diseaseModel = new DiseaseModelHibImp();
			}
			modelCache.put("diseaseModel", diseaseModel);
		}

		return diseaseModel;
	}

	public DoctorModelInt getDoctorModel() {

		DoctorModelInt doctorModel = (DoctorModelInt) modelCache.get("doctorModel");
		if (doctorModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				doctorModel = new DoctorModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				doctorModel = new DoctorModelHibImp();
			}
			modelCache.put("doctorModel", doctorModel);
		}

		return doctorModel;
	}

	public DoctorScheduleModelInt getDoctorScheduleModel() {

		DoctorScheduleModelInt doctorScheduleModel = (DoctorScheduleModelInt) modelCache.get("doctorScheduleModel");
		if (doctorScheduleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				doctorScheduleModel = new DoctorScheduleModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				doctorScheduleModel = new DoctorScheduleModelHibImp();
			}
			modelCache.put("doctorScheduleModel", doctorScheduleModel);
		}

		return doctorScheduleModel;
	}

	public PatientModelInt getPatientModel() {

		PatientModelInt patientModel = (PatientModelInt) modelCache.get("patientModel");
		if (patientModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				patientModel = new PatientModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				patientModel = new PatientModelHibImp();
			}
			modelCache.put("patientModel", patientModel);
		}

		return patientModel;
	}
	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelHibImp();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}
	
	public SpecialistModelInt getSpecialistModel() {
		SpecialistModelInt specialistModel = (SpecialistModelInt) modelCache.get("specialistModel");
		if (specialistModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				specialistModel = new SpecialistModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				specialistModel = new SpecialistModelHibImp();
			}
			modelCache.put("specialistModel", specialistModel);
		}
		return specialistModel;
	}
	
	public WardModelInt getWardModel() {
		WardModelInt wardModel = (WardModelInt) modelCache.get("wardModel");
		if (wardModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				wardModel = new WardModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				wardModel = new WardModelHibImp();
			}
			modelCache.put("wardModel", wardModel);
		}
		return wardModel;
	}
	
}
