package ar.com.unla.api.utils;

import ar.com.unla.api.models.database.ExamenFinal;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinalesPDFExporter {

    private List<ExamenFinal> finalesMañana;

    private List<ExamenFinal> finalesTarde;

    private List<ExamenFinal> finalesNoche;

    private void writeTableTitle(Document document, String titulo) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(new Color(138, 45, 45));
        font.setSize(18);

        Paragraph title = new Paragraph(titulo, font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(15);
        document.add(title);
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(138, 45, 45));
        cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Año", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Materia", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Inscripción", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fecha", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Horario", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<ExamenFinal> finales) {

        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);

        if (finales != null && !finales.isEmpty()) {
            for (ExamenFinal examenFinal : Collections.unmodifiableList(finales)) {

                if (examenFinal.getMateria() != null) {

                    cell.setPhrase(
                            new Phrase(String.valueOf(examenFinal.getMateria().getAnioCarrera())));
                    table.addCell(cell);

                    cell.setPhrase(new Phrase(examenFinal.getMateria().getNombre()));
                    table.addCell(cell);

                    if (examenFinal.getPeriodoInscripcion() != null) {

                        PdfPCell datesCell = new PdfPCell();
                        datesCell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
                        datesCell.setNoWrap(true);
                        datesCell.setExtraParagraphSpace(5);

                        String inscripcion =
                                " Desde: " + examenFinal.getPeriodoInscripcion().getFechaDesde()
                                        .toString() + "\n" +
                                        " Hasta: " + examenFinal.getPeriodoInscripcion()
                                        .getFechaHasta()
                                        .toString();
                        datesCell.setPhrase(new Phrase(inscripcion));
                        datesCell.setPaddingTop(4);
                        datesCell.setPaddingBottom(8);
                        table.addCell(datesCell);
                    }

                    cell.setPhrase(new Phrase(examenFinal.getFecha().toString()));
                    table.addCell(cell);

                    String horario = examenFinal.getMateria().getTurno().getHoraDesde() + " - " +
                            examenFinal.getMateria().getTurno().getHoraHasta();
                    cell.setPhrase(new Phrase(horario));
                    table.addCell(cell);
                }
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        writeTableTitle(document, "Finales UNLa");

        PdfPTable tablaManiana = new PdfPTable(5);
        tablaManiana.setWidthPercentage(100);
        tablaManiana.setWidths(new float[]{15f, 20f, 25f, 20f, 20f});

        writeTableTitle(document, "Turno mañana");
        writeTableHeader(tablaManiana);
        writeTableData(tablaManiana, finalesMañana);
        document.add(tablaManiana);

        PdfPTable tablaTarde = new PdfPTable(5);
        tablaTarde.setWidthPercentage(100);
        tablaTarde.setWidths(new float[]{15f, 20f, 25f, 20f, 20f});

        writeTableTitle(document, "Turno tarde");
        writeTableHeader(tablaTarde);
        writeTableData(tablaTarde, finalesTarde);
        document.add(tablaTarde);

        PdfPTable tablaNoche = new PdfPTable(5);
        tablaNoche.setWidthPercentage(100);
        tablaNoche.setWidths(new float[]{15f, 20f, 25f, 20f, 20f});

        writeTableTitle(document, "Turno noche");
        writeTableHeader(tablaNoche);
        writeTableData(tablaNoche, finalesNoche);
        document.add(tablaNoche);

        document.close();
    }

}
