package dev.slne.event.goal;

import java.util.ArrayList;
import java.util.List;

import dev.slne.event.goal.goals.JumpscareGoal;
import dev.slne.event.goal.goals.KillGoal;
import dev.slne.event.goal.goals.RewindGoal;

public class GoalManager {

    private List<Goal> goals;

    public GoalManager() {
        this.goals = new ArrayList<>();

        registerGoals();
    }

    public void registerGoals() {
        this.goals.add(new RewindGoal());
        // this.goals.add(new TwitchControlGoal());
        this.goals.add(new JumpscareGoal());
        this.goals.add(new KillGoal());
    }

    public Goal getGoal(String goalName) {
        return this.goals.stream().filter(goal -> goal.getName().equals(goalName)).findFirst().orElse(null);
    }

    public List<Goal> getGoals() {
        return goals;
    }

}
