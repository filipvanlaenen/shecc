package net.filipvanlaenen.shecc;

import java.util.ArrayList;
import java.util.List;

import net.filipvanlaenen.shecc.export.SeatingPlanExporter;

/**
 * The command-line interface for this library.
 */
public class CommandLineInterface {

    /**
     * The index of the size in the parliamentary group encoding.
     */
    private static final int SIZE_INDEX = 0;
    /**
     * The index of the color in the parliamentary group encoding.
     */
    private static final int COLOR_INDEX = 1;
    /**
     * The index of the name in the parliamentary group encoding.
     */
    private static final int NAME_INDEX = 2;
    /**
     * The index of the character in the parliamentary group encoding.
     */
    private static final int CHARACTER_INDEX = 3;
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
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String groupsDefinition = parseArgumentsAndReturnGroupsDefinitionString(exporter, args);
        String[] groupdefinitions = groupsDefinition.split(",");
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        boolean atLeastOneNamePresent = false;
        for (int i = 0; i < groupdefinitions.length; i++) {
            String[] attributes = groupdefinitions[i].split("\\.");
            GroupSize size = GroupSize.parseGroupSize(attributes[SIZE_INDEX]);
            int color = Integer.parseInt(attributes[COLOR_INDEX], SIXTEEN);
            String name = attributes.length > NAME_INDEX ? attributes[NAME_INDEX] : null;
            atLeastOneNamePresent |= name != null && !name.isEmpty();
            String character = attributes.length > CHARACTER_INDEX ? attributes[CHARACTER_INDEX] : null;
            groups.add(new ParliamentaryGroup(size, color, name, character));
        }
        SeatingPlan plan = new SeatingPlan(groups);
        exporter.setDisplayLegend(atLeastOneNamePresent);
        return exporter.export(plan);
    }

    /**
     * Parses all arguments and sets them on the exporter, and returns the argument
     * containing the groups definitions.
     *
     * @param exporter
     *            The exporter on which to apply the arguments.
     * @param args
     *            The arguments from the command-line.
     * @return The argument with the groups definition.
     */
    private String parseArgumentsAndReturnGroupsDefinitionString(final SeatingPlanExporter exporter,
            final String... args) {
        String groupsDefinition = null;
        for (String argument : args) {
            if (argument.startsWith("--")) {
                String[] keyValue = argument.substring(2).split("=");
                String key = keyValue[0];
                String value = keyValue[1];
                if (key.equals("background-color")) {
                    exporter.setBackgroundColor(Integer.parseInt(value, SIXTEEN));
                } else if (key.equals("copyright-notice")) {
                    exporter.setCustomCopyrightNotice(value);
                } else if (key.equals("font-color")) {
                    exporter.setFontColor(Integer.parseInt(value, SIXTEEN));
                } else if (key.equals("font-family")) {
                    exporter.setFontFamily(value);
                } else if (key.equals("legend-label-width-ratio")) {
                    exporter.setLegendLabelWidthRatio(Integer.parseInt(value));
                } else if (key.equals("subtitle")) {
                    exporter.setSubtitle(value);
                } else if (key.equals("title")) {
                    exporter.setTitle(value);
                }
            } else {
                groupsDefinition = argument;
            }
        }
        return groupsDefinition;
    }

}
