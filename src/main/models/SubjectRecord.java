package main.models;

public class SubjectRecord {
    private final String subjectId;
    private final String subjectName;
    private final Section enrolledSection;

    public SubjectRecord(String subjectId, String subjectName, Section enrolledSection) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.enrolledSection = enrolledSection;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Section getEnrolledSection() {
        return enrolledSection;
    }
}
