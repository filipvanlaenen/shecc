# Scalable Hemicycle Charts (SHecC)

This project aims to produce scalable hemicycle charts. Below is a short list
of the features it intends to implement:

* Find an optimal distribution of the seats based on the following input
  parameters:
  * The number of mandates
  * The ratio between the inner and the outer radius of the hemicycle
  * The angle of the hemicycle
* Distribute the parties across the hemicycle, and mark each seat using:
  * The party color, or for electoral alliances, a set of colors
  * A circle or a polygon, a person shape or a seat shape
  * A character
* Optionally, add a legend displaying:
  * The shape, the colors and the character per party
  * Party name
  * Number of mandates

## Getting Started

First of all, you need to obtain a copy of the source code and compile it into
an executable. Run the following commands to do this:

```
git clone git@github.com:filipvanlaenen/shecc.git
cd shecc
mvn clean compile assembly:single
```

If everything works well, you'll a JAR file in the `target` directory with all
dependencies included. Navigate into the `target` directory and produce your
first seating plan as folows:

```
cd target
java -jar shecc-1.0-SNAPSHOT-jar-with-dependencies.jar 19.FF0000..R,11.00FF00..G,48.0000FF..B
```

As you'll see, this will print the contents of an SVG file to the command line.
You can redirect the output to an SVG file, and if you have
[Inkscape](https://inkscape.org/) installed, convert it to a PNG file as
follows:

```
java -jar shecc-1.0-SNAPSHOT-jar-with-dependencies.jar 19.FF0000..R,11.00FF00..G,48.0000FF..B > seating-plan.svg
inkscape -z -D seating-plan.svg -e seating-plan.png
```

The result should be something like this, a seating plan with 19 red seats, 11
green seats and 48 blue seats:

![Seating plan with 19 red seats, 11 green seats and 48 blue seats](/README-seating-plan.png "Seating plan with 19 red seats, 11 green seats and 48 blue seats")

## Seating Plan Specification

The number of seats per parliamentary group can be specified as follows:
* For each group, specify its size, followed by a dot, followed by its color as
  a six-digit hexademical number, followed by a dot, followed by a name,
  followed by a dot, followed by a character to be used in the circle
* Join all groups together, separating them with a comma
* Parliamentary groups will be placed in the hemicycle from left to right

The example from above, `19.FF0000..R,11.00FF00..G,48.0000FF..B` can therefore be
decoded as follows:

| Group Specification | Size | Color            | Character |
|:-------------------:|:----:|:----------------:|:---------:|
| `19.FF0000..R`      | 19   | `FF0000` (red)   | R         |
| `11.00FF00..G`      | 11   | `00FF00` (green) | G         |
| `48.0000FF..B`      | 48   | `0000FF` (blue)  | B         |

Now lets add names for the parliamentary groups. You can do this as follows 
(notice that you have to use quotes around the command-line argument if any of
the names contains a space):

```
java -jar shecc-1.0-SNAPSHOT-jar-with-dependencies.jar \
  "19.FF0000.Red Party.R,11.00FF00.Green Party.G,48.0000FF.Blue Party.B" > seating-plan-with-legend.svg
```

![Seating plan with 19 red seats, 11 green seats and 48 blue seats, together with a legend](/README-seating-plan-with-legend.png "Seating plan with 19 red seats, 11 green seats and 48 blue seats, together with a legend")

Below is a more formal specification of the grammar in extended Backus-Naur
form:

```
letter              = "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N"
                    | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z" | "a" | "b"
                    | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p"
                    | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z" ;
digit               = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" ;
hexademical digit   = digit | "A" | "a" | "B" | "b" | "C" | "c" | "D" | "d" | "E" | "e" | "F" | "f" ;
integer             = digit , { digit } ;
size                = integer ;
color               = hexademical digit , hexademical digit , hexademical digit , hexademical digit ,
                      hexademical digit , hexademical digit ;
character           = letter | digit ;
space               = " " ;
name                = { letter | digit | space } ;
dot                 = "." ;
group specification = size , dot , color , dot , name , dot , character ;
comma               = "," ;
seating plan        = group specification ,  { comma , group specification } ;
```

## Chart Styling

There are a few command-line options available to style the chart produced. The
table below gives an overview.

| Option              | Type   | Description                    |
|---------------------|--------|--------------------------------|             
| background-color    | Color  | Sets the background color      |            
| copyright-notice    | String | Sets a custom copyright notice |            
| font-color          | Color  | Sets the font color            |            
| font-family         | String | Sets the font family           |
| subtitle            | String | Sets the subtitle              |
| title               | String | Sets the title                 |

The following command will produce a chart using the Lato font, and with a dark
blue as the font color:

```
java -jar shecc-1.0-SNAPSHOT-jar-with-dependencies.jar \
  "19.FF0000.Red Party.R,11.00FF00.Green Party.G,48.0000FF.Blue Party.B" \
  --font-family=Lato --font-color=000077 > styled-seating-plan-with-legend.svg
```

![Styled seating plan with 19 red seats, 11 green seats and 48 blue seats, together with a legend](/README-styled-seating-plan-with-legend.png "Styled seating plan with 19 red seats, 11 green seats and 48 blue seats, together with a legend")
