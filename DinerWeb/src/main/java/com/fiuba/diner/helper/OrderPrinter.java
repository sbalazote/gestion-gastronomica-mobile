package com.fiuba.diner.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.model.Waiter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class OrderPrinter {

	private String restaurantName;
	private String address;
	private Double dinerServicePrice;
	private String userName;
	private String filepath;
	public String RESULT;

	/** Inner class to add a header and a footer. */
	class HeaderFooter extends PdfPageEventHelper {

		Phrase[] header = new Phrase[2];
		int pagenumber;

		@Override
		public void onOpenDocument(PdfWriter writer, Document document) {
			this.header[0] = new Phrase("");
		}

		@Override
		public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title) {
			this.header[1] = new Phrase(title.getContent());
			this.pagenumber = 1;
		}

		@Override
		public void onStartPage(PdfWriter writer, Document document) {
			this.pagenumber++;
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			Rectangle rect = writer.getBoxSize("art");

			String restaurantName = OrderPrinter.this.restaurantName;
			String user = OrderPrinter.this.userName;
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
					new Phrase(String.format("%s \t Usuario: %s", restaurantName.toUpperCase(), user.toUpperCase())), (rect.getLeft() + rect.getRight()) / 2,
					rect.getBottom() - 18, 0);
		}
	}

	public void createPdf(String restaurantName, String address, Double dinerServicePrice, String userName, String filepath, Order order)
			throws DocumentException, IOException {
		this.restaurantName = restaurantName;
		this.address = address;
		this.dinerServicePrice = dinerServicePrice;
		this.userName = userName;
		this.filepath = filepath;

		// paso 1
		Document document = new Document(PageSize.A4, 0f, 0f, 0f, 0f);

		// paso 2
		this.RESULT = this.filepath + "order-number-" + order.getId() + ".pdf";
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(this.RESULT));
		HeaderFooter event = new HeaderFooter();
		pdfWriter.setBoxSize("art", new Rectangle(36, 54, 559, 788));
		pdfWriter.setPageEvent(event);

		// paso 3
		document.open();

		// paso 4
		document.add(this.addHeaderTable());
		document.add(this.addBodyDataTable(order));
		this.addDetailsTable(document, order);

		// paso 5
		document.close();
	}

	private PdfPTable addHeaderTable() {
		PdfPTable header = new PdfPTable(20);
		header.setWidthPercentage(75);
		header.setSpacingBefore(0);
		header.setSpacingAfter(0);

		PdfPCell cell = null;

		cell = new PdfPCell(new Paragraph(this.restaurantName));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(12);
		header.addCell(cell);

		cell = new PdfPCell(new Paragraph(this.address));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(12);
		header.addCell(cell);

		cell = new PdfPCell(new Paragraph("Ordenado por Cliente.\nReporte Detallado"));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(7);
		header.addCell(cell);

		return header;
	}

	private PdfPTable addBodyDataTable(Order order) {

		PdfPTable header = new PdfPTable(1);
		header.setWidthPercentage(75);
		header.setSpacingBefore(0);
		header.setSpacingAfter(0);

		PdfPCell cell = null;

		cell = new PdfPCell(new Paragraph(this.restaurantName));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);

		Waiter waiter = order.getTables().get(0).getWaiter();
		Table table = order.getTables().get(0);
		cell = new PdfPCell(new Paragraph("Mozo: " + waiter.getSurname() + ", " + waiter.getName() + " - Mesa Nro: " + table.getId()));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);

		cell = new PdfPCell(new Paragraph(new SimpleDateFormat("'Fecha:' dd/MM/yyyy 'Hora:' HH:mm:ss").format(new Date())));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);

		return header;
	}

	private void addDetailsTable(Document document, Order order) throws DocumentException {
		PdfPTable header = new PdfPTable(21);
		header.setWidthPercentage(75);
		header.setSpacingBefore(0);
		header.setSpacingAfter(0);

		PdfPCell cell = null;

		cell = new PdfPCell(new Paragraph("Id_prod"));
		cell.setColspan(3);
		header.addCell(cell);

		cell = new PdfPCell(new Paragraph("Producto"));
		cell.setColspan(12);
		header.addCell(cell);

		cell = new PdfPCell(new Paragraph("Cantidad"));
		cell.setColspan(3);
		header.addCell(cell);

		cell = new PdfPCell(new Paragraph("Precio"));
		cell.setColspan(3);
		header.addCell(cell);

		document.add(header);

		// DETALLES
		Integer productId = 0;
		String productDescription = "";
		Integer productAmount = 0;
		Double productPrice = 0.0;
		Double subtotal = 0.0;
		Double total = 0.0;
		Double couponDiscount = 0.0;

		Iterator<OrderDetail> it = order.getDetails().iterator();
		while (it.hasNext()) {
			OrderDetail od = it.next();
			productId = od.getProduct().getId();
			productDescription = od.getProduct().getDescription();
			productAmount = od.getAmount();
			productPrice = od.getProduct().getPrice();
			subtotal += productAmount * productPrice;
			this.printDetail(document, header, cell, productId.toString(), productDescription, productAmount.toString(), productPrice.toString());
		}

		// imprimo subtotal
		this.printDetail(document, header, cell, "-", "SUBTOTAL", "-", subtotal.toString());

		// imprimo servicio de mesa
		Double dinerServicePriceTotal = this.dinerServicePrice * order.getCustomerAmount();
		this.printDetail(document, header, cell, "-", "Servicio de Mesa", order.getCustomerAmount().toString(), dinerServicePriceTotal.toString());
		if (order.getCoupon() != null) {
			couponDiscount = (dinerServicePriceTotal + subtotal) * (order.getCoupon().getPercentage());
			this.printDetail(document, header, cell, "-", order.getCoupon().getDescription(), " - ", dinerServicePriceTotal.toString());
		}

		// imprimo total
		total = subtotal + dinerServicePriceTotal - couponDiscount;
		this.printDetail(document, header, cell, "-", "TOTAL", "-", total.toString());
	}

	private void printDetail(Document document, PdfPTable header, PdfPCell cell, String id, String description, String amount, String price)
			throws DocumentException {
		header = new PdfPTable(21);
		header.setWidthPercentage(75);
		header.setSpacingBefore(0);
		header.setSpacingAfter(0);

		cell = new PdfPCell(new Paragraph(id));
		cell.setColspan(3);
		header.addCell(cell);

		cell = new PdfPCell(new Paragraph(description));
		cell.setColspan(12);
		header.addCell(cell);

		cell = new PdfPCell(new Paragraph(amount));
		cell.setColspan(3);
		header.addCell(cell);

		cell = new PdfPCell(new Paragraph(price));
		cell.setColspan(3);
		header.addCell(cell);

		document.add(header);
	}
}
