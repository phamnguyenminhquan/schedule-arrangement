package config;

import java.nio.file.Path;

import repositories.SectionRepo;
import repositories.StudentRepo;
import repositories.SubjectRepo;
import services.StorageServices.JSONSectionStorage;
import services.StorageServices.JSONStudentStorage;
import services.StorageServices.JSONSubjectStorage;

public class AppConfig {
    private static final Path STUDENT_PATH = Path.of("data/students.json");
    private static final Path SUBJECT_PATH = Path.of("data/subjects.json");
    private static final Path SECTION_PATH = Path.of("data/sections.json");

    // config repo and initialize storage
    private static final StudentRepo studentRepo = StudentRepo.getInstance()
            .initialize(new JSONStudentStorage(STUDENT_PATH));

    private static final SubjectRepo subjectRepo = SubjectRepo.getInstance()
            .initialize(new JSONSubjectStorage(SUBJECT_PATH));

    private static final SectionRepo sectionRepo = SectionRepo.getInstance()
            .initialize(new JSONSectionStorage(SECTION_PATH));

    // #region getters
    public static StudentRepo getStudentRepo() {
        return studentRepo;
    }

    public static SubjectRepo getSubjectRepo() {
        return subjectRepo;
    }

    public static SectionRepo getSectionRepo() {
        return sectionRepo;
    }

    public static Path getStudentPath() {
        return STUDENT_PATH;
    }

    public static Path getSubjectPath() {
        return SUBJECT_PATH;
    }

    public static Path getSectionPath() {
        return SECTION_PATH;
    }
    // #endregion
}
