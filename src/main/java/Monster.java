import org.sql2o.Connection;
import java.util.Timer;

import java.sql.Timestamp;
import java.util.List;

public abstract class Monster {
    public String name;
    public int personId;
    public int id;
    public int foodLevel;
    public int sleepLevel;
    public int playLevel;
    public Timestamp birthday;
    public Timestamp lastSlept;
    public Timestamp lastPlayed;
    public Timestamp lastAte;

    public Timer timer;


    public static final int MAX_FOOD_LEVEL = 3;
    public static final int MAX_SLEEP_LEVEL = 8;
    public static final int MAX_PLAY_LEVEL = 12;
    public static final int MIN_ALL_LEVELS = 0;

    public Timestamp getLastSlept() { return lastSlept; }
    public Timestamp getLastPlayed() { return lastPlayed; }
    public Timestamp getLastAte() { return lastAte; }
    public String getName() {
        return name;
    }
    public int getPersonId() {
        return personId;
    }
    public int getId() {
        return id;
    }
    public int getPlayLevel(){
        return playLevel;
    }
    public int getSleepLevel(){
        return sleepLevel;
    }
    public int getFoodLevel(){
        return foodLevel;
    }
    public Timestamp getBirthday() {
        return birthday;
    }

    @Override
    public boolean equals(Object otherMonster){
        if (!(otherMonster instanceof Monster)) {
            return false;
        } else {
            Monster newMonster = (Monster) otherMonster;
            return this.getName().equals(newMonster.getName()) &&
                    this.getPersonId() == newMonster.getPersonId();
        }
    }



    public boolean isAlive() {
        if (foodLevel <= MIN_ALL_LEVELS ||
                playLevel <= MIN_ALL_LEVELS ||
                sleepLevel <= MIN_ALL_LEVELS) {
            return false;
        }
        return true;
    }
    public void depleteLevels(){
        playLevel--;
        foodLevel--;
        sleepLevel--;
    }
    public void play(){
        if (playLevel >= MAX_PLAY_LEVEL){
            throw new UnsupportedOperationException("You cannot play with monster anymore!");
        }
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE monsters SET lastplayed = now() WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
        playLevel++;
    }
    public void sleep(){
        if (sleepLevel >= MAX_SLEEP_LEVEL){
            throw new UnsupportedOperationException("You cannot make your monster sleep anymore!");
        }
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE monsters SET lastslept = now() WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
        sleepLevel++;
    }
    public void feed(){
        if (foodLevel >= MAX_FOOD_LEVEL){
            throw new UnsupportedOperationException("You cannot feed your monster anymore!");
        }
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE monsters SET lastate = now() WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
        foodLevel++;
    }

    public void save(){
        try(Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO monsters (name, personId, birthday, lastate, lastslept, lastplayed) VALUES (:name, :personId, now(), now(), now(),now())";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name",this.name)
                    .addParameter("personId", this.personId)
                    .executeUpdate()
                    .getKey();
        }

    }

}
