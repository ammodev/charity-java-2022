package dev.slne.event.donation.listeners;

import java.util.Arrays;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import dev.slne.event.Main;
import dev.slne.event.donation.CharityDonation;
import dev.slne.event.donation.CharityEvent;
import dev.slne.event.donation.DonationIgnitor;
import dev.slne.event.goal.Goal;
import dev.slne.event.goal.nonGoals.BlindnessNonGoal;
import dev.slne.event.goal.nonGoals.BurnNonGoal;
import dev.slne.event.goal.nonGoals.CrucioNonGoal;
import dev.slne.event.goal.nonGoals.FreezeNonGoal;
import dev.slne.event.goal.nonGoals.LevitationNonGoal;
import dev.slne.event.goal.nonGoals.NonGoal;
import dev.slne.event.goal.nonGoals.TntRainNonGoal;
import dev.slne.event.utils.DonationUtils;

public class DonationListener implements Listener {

    @EventHandler
    public void onDonation(CharityEvent event) {
        CharityDonation donation = event.getCharityDonation();
        String message = donation.getMessage();
        double donationAmount = donation.getAmountNet();

        if (message == null) {
            message = "";
            donation.setMessage("");
        }

        String[] messageSplit = message.trim().split(" ");

        boolean foundMatch = false;
        boolean isGoal = false;

        for (String string : messageSplit) {
            if (!string.startsWith("#")) {
                continue;
            }

            if (string.length() <= 1) {
                continue;
            }

            DonationIgnitor ignitor = (DonationIgnitor) Main.getInstance().getIgnitor(DonationIgnitor.class);

            if (ignitor == null) {
                return;
            }

            String hashtag = string.substring(1).toLowerCase();
            Goal goal = ignitor.getGoalManager().getGoals().stream()
                    .filter(goalItem -> goalItem.getName().equalsIgnoreCase(hashtag)).findFirst().orElse(null);

            if (goal != null) {
                goal.addAmount(donationAmount);

                DonationUtils.broadcastDonationMessage(donation,
                        goal.getSpellName() != null ? goal.getSpellName() : goal.getDisplayName());
                foundMatch = true;
                isGoal = true;
                break;
            }

            NonGoal matchNonGoal = matchNonGoal(donationAmount, hashtag, message);

            if (matchNonGoal != null) {
                DonationUtils.broadcastDonationMessage(donation, matchNonGoal.getSpellName());
                matchNonGoal.execute();
                foundMatch = true;
                break;
            }
        }

        if (!foundMatch) {
            DonationUtils.broadcastDonationMessage(donation, null);
        }

        if (!isGoal) {
            DonationUtils.broadcastDonationToast(donation);
        }
    }

    private NonGoal matchNonGoal(double donationAmount, String hashtag, String message) {
        NonGoal matchNonGoal = null;

        if (donationAmount >= 5) {
            if (Arrays.asList("crucio", "gift").stream().map(spell -> spell.toLowerCase()).toList()
                    .contains(hashtag.toLowerCase())) {
                matchNonGoal = new CrucioNonGoal();
                return matchNonGoal;
            }
        }

        if (donationAmount >= 10) {
            if (Arrays.asList("incendio", "brennen", "burn", "brenn", "feuer").stream()
                    .map(spell -> spell.toLowerCase()).toList()
                    .contains(hashtag.toLowerCase())) {
                matchNonGoal = new BurnNonGoal();
                return matchNonGoal;
            } else if (Arrays.asList("nox", "blind", "blindness").stream().map(spell -> spell.toLowerCase()).toList()
                    .contains(hashtag.toLowerCase())) {
                matchNonGoal = new BlindnessNonGoal();
                return matchNonGoal;
            }
        }

        if (donationAmount >= 25) {
            if (Arrays.asList("tnt", "bombarda").stream().map(spell -> spell.toLowerCase()).toList()
                    .contains(hashtag.toLowerCase())) {
                matchNonGoal = new TntRainNonGoal();
                return matchNonGoal;
            } else if (Arrays.asList("freeze", "petrificustotalus").stream().map(spell -> spell.toLowerCase()).toList()
                    .contains(hashtag.toLowerCase())) {
                matchNonGoal = new FreezeNonGoal();
                return matchNonGoal;
            }
        }

        if (donationAmount >= 50) {
            if (Arrays.asList("levitation", "leviosa", "wingardium", "wingardiumleviosa", "fliegen", "abheben").stream()
                    .map(spell -> spell.toLowerCase()).toList()
                    .contains(hashtag.toLowerCase())) {
                matchNonGoal = new LevitationNonGoal();
                return matchNonGoal;
            }
        }

        return matchNonGoal;
    }

}
