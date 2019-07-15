package net.filipvanlaenen.shecc;

import java.util.ArrayList;
import java.util.List;

import net.filipvanlaenen.shecc.export.SeatingPlanExporter;

/**
 * The command-line interface for this library.
 */
public class CommandLineInterface {

    /**
     * Magic number sixteen, the base for parsing hexadecimal numbers.
     */
    private static final int SIXTEEN = 16;

    /**
     * Main entry point for the command-line interface.
     *
     * @param args
     *            The arguments from the command-line.
     */
    public static void main(final String... args) {
        System.out.println(new CommandLineInterface().perform(args));
    }

    /**
     * Performs the action requested from the command-line.
     *
     * @param args
     *            The arguments from the command-line.
     * @return Whatever was requested by the user from the command-line.
     */
    String perform(final String... args) {
        String groupsargument = args[0];
        String[] groupdefinitions = groupsargument.split(",");
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        for (int i = 0; i < groupdefinitions.length; i++) {
            String[] attributes = groupdefinitions[i].split("\\.");
            int size = Integer.parseInt(attributes[0]);
            int color = Integer.parseInt(attributes[1], SIXTEEN);
            String character = attributes[2];
            groups.add(new ParliamentaryGroup(size, color, character));
        }
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        return exporter.export(plan);
    }

}
