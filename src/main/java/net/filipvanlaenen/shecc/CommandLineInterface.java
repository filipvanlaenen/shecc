package net.filipvanlaenen.shecc;

import java.util.ArrayList;
import java.util.List;

import net.filipvanlaenen.shecc.export.SeatingPlanExporter;

public class CommandLineInterface {

    public static void main(String... args) {
        System.out.println(new CommandLineInterface().perform(args));
    }

    String perform(String... args) {
        String groupsargument = args[0];
        String[] groupdefinitions = groupsargument.split(",");
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        for (int i = 0; i < groupdefinitions.length; i++) {
            String[] attributes = groupdefinitions[i].split("\\.");
            int size = Integer.parseInt(attributes[0]);
            int color = Integer.parseInt(attributes[1], 16);
            String character = attributes[2];
            groups.add(new ParliamentaryGroup(size, color, character));
        }
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        return exporter.export(plan);
    }

}
