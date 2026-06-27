package services.RegistrationServices;

import services.responses.Result;

public interface RegistrationService {
    Result register(String studentId, String sectionId);

    Result unregister(String studentId, String sectionId);
}
