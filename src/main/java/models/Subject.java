package models;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Subject {
    @NonNull
    private final String id;

    @NonNull
    private final String name;

    private final int noCredits;

    @Override
    public String toString() {
        return "Subject [id=" + id + ", name=" + name + ", noCredits=" + noCredits + "]";
    }
}
