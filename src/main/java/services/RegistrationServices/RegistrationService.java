package services.RegistrationServices;

import services.responses.CheckResult;

public interface RegistrationService {
    CheckResult register(String studentId, String sectionId);

    CheckResult unregister(String studentId, String sectionId);
}
