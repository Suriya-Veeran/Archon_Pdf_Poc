package utility;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import static com.p3solutions.archon_report_utility.constants.ChartFontSizeConstants.*;
import static com.p3solutions.archon_report_utility.constants.SpecialCharacterConstants.SPACE;
import static utility.HexToRgb.hexToRgb;

public class AddColorRowToTable {
    public static void addColorRowToTable(Table table,
                                           String colorHex,
                                           String label,
                                           String value) {
        table.addCell(
                new Cell()
                        .add(
                                new Paragraph(SPACE)
                                        .setBackgroundColor(hexToRgb(colorHex))
                                        .setWidth(FOUR)
                                        .setHeight(FOUR))
                        .setBorder(Border.NO_BORDER));

        table.addCell(
                new Cell()
                        .add(new Paragraph(label))
                        .setFontSize(FIVE)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setPadding(TWO)
                        .setBorder(Border.NO_BORDER)
                        .setWordSpacing(ONE));

        table.addCell(
                new Cell()
                        .add(new Paragraph(value))
                        .setFontSize(FIVE)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setPadding(TWO)
                        .setBorder(Border.NO_BORDER)
                        .setWordSpacing(ONE));
    }
}
