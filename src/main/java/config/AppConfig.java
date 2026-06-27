package config;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import repositories.SectionRepo;
import repositories.StudentRepo;
import repositories.SubjectRepo;
import services.RegistrationCheckers.DuplicateSectionChecker;
import services.RegistrationCheckers.DuplicateSubjectChecker;
import services.RegistrationCheckers.RegistrationChecker;
import services.RegistrationCheckers.ScheduleConflictChecker;
import services.RegistrationCheckers.SectionCapacityChecker;
import services.RegistrationServices.RegistrationService;
import services.RegistrationServices.StandardRegistration;
import services.StorageServices.JSONSectionStorage;
import services.StorageServices.JSONStudentStorage;
import services.StorageServices.JSONSubjectStorage;
import services.responses.ErrorType;
import services.responses.Result;

public class AppConfig {
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);

    // data storage path
    private static final Path STUDENT_PATH = Path.of("data/students.json");
    private static final Path SUBJECT_PATH = Path.of("data/subjects.json");
    private static final Path SECTION_PATH = Path.of("data/sections.json");

    // repositories
    private static final StudentRepo studentRepo = StudentRepo.getInstance();
    private static final SubjectRepo subjectRepo = SubjectRepo.getInstance();
    private static final SectionRepo sectionRepo = SectionRepo.getInstance();

    // services
    private static final Map<ErrorType, RegistrationChecker> checkerMap = new HashMap<>();
    private static RegistrationService registrationService;

    /**
     * Initializes AppConfig
     * 
     * @throws IllegalStateException    if any repository has already been
     *                                  initialized
     * @throws IllegalArgumentException if any storage service is null
     */
    public static void initialize() {
        if (!isInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("AppConfig has already been initialized");
        }

        // initialize storage
        studentRepo.initialize(new JSONStudentStorage(STUDENT_PATH));
        subjectRepo.initialize(new JSONSubjectStorage(SUBJECT_PATH));
        sectionRepo.initialize(new JSONSectionStorage(SECTION_PATH));

        // config registration checkers
        checkerMap.put(ErrorType.CAPACITY_FULL, new SectionCapacityChecker());
        checkerMap.put(ErrorType.DUPLICATE_SECTIONS, new DuplicateSectionChecker());
        checkerMap.put(ErrorType.DUPLICATE_SUBJECTS, new DuplicateSubjectChecker(sectionRepo));
        checkerMap.put(ErrorType.SCHEDULE_CONFLICT, new ScheduleConflictChecker(sectionRepo));

        registrationService = new StandardRegistration(studentRepo, subjectRepo, sectionRepo, checkerMap);
    }

    // #region getters
    public static Path getStudentPath() {
        return STUDENT_PATH;
    }

    public static Path getSubjectPath() {
        return SUBJECT_PATH;
    }

    public static Path getSectionPath() {
        return SECTION_PATH;
    }

    public static StudentRepo getStudentRepo() {
        return studentRepo;
    }

    public static SubjectRepo getSubjectRepo() {
        return subjectRepo;
    }

    public static SectionRepo getSectionRepo() {
        return sectionRepo;
    }

    public static Map<ErrorType, RegistrationChecker> getCheckerMap() {
        return checkerMap;
    }

    public static RegistrationService getRegistrationservice() {
        return registrationService;
    }

    // #endregion

    public static Result repoLoading() {
        Result studentLoad = studentRepo.load();
        if (!studentLoad.isValid()) {
            return studentLoad;
        }

        Result sectionLoad = sectionRepo.load();
        if (!sectionLoad.isValid()) {
            return sectionLoad;
        }

        Result subjectLoad = subjectRepo.load();
        if (!subjectLoad.isValid()) {
            return subjectLoad;
        }

        return Result.success();
    }

    public static Result repoSaving() {
        Result studentSave = studentRepo.save();
        if (!studentSave.isValid()) {
            return studentSave;
        }

        Result sectionSave = sectionRepo.save();
        if (!sectionSave.isValid()) {
            return sectionSave;
        }

        Result subjectSave = subjectRepo.save();
        if (!subjectSave.isValid()) {
            return subjectSave;
        }

        return Result.success();
    }
}
