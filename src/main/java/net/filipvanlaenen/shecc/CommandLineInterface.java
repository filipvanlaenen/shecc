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
        String groupsDefinition = null;
        String customCopyrightNotice = null;
        String fontFamily = null;
        Integer fontColor = null;
        for (String argument : args) {
            if (argument.startsWith("--")) {
                String[] keyValue = argument.substring(2).split("=");
                String key = keyValue[0];
                String value = keyValue[1];
                if (key.equals("copyright-notice")) {
                    customCopyrightNotice = value;
                } else if (key.equals("font-family")) {
                    fontFamily = value;
                } else if (key.equals("font-color")) {
                    fontColor = Integer.parseInt(value, SIXTEEN);
                }
            } else {
                groupsDefinition = argument;
            }
        }
        String[] groupdefinitions = groupsDefinition.split(",");
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        boolean atLeastOneNamePresent = false;
        for (int i = 0; i < groupdefinitions.length; i++) {
            String[] attributes = groupdefinitions[i].split("\\.");
            int size = Integer.parseInt(attributes[0]);
            int color = Integer.parseInt(attributes[1], SIXTEEN);
            String name = attributes[2];
            atLeastOneNamePresent |= !name.isEmpty();
            String character = attributes[3];
            groups.add(new ParliamentaryGroup(size, color, name, character));
        }
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        if (customCopyrightNotice != null) {
            exporter.setCustomCopyrightNotice(customCopyrightNotice);
        }
        exporter.setDisplayLegend(atLeastOneNamePresent);
        if (fontColor != null) {
            exporter.setFontColor(fontColor);
        }
        if (fontFamily != null) {
            exporter.setFontFamily(fontFamily);
        }
        return exporter.export(plan);
    }

}
