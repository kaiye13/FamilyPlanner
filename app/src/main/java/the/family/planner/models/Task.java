package the.family.planner.models;

public class Task {
    private String start_time, end_time, title, description, date, task_id, user_id;

    public Task(String start_time, String end_time, String title, String description, String date, String user_id) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.title = title;
        this.description = description;
        this.date = date;
        this.task_id = task_id;
        this.user_id = user_id;
    }

    public Task() {

    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", task_id='" + task_id + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
