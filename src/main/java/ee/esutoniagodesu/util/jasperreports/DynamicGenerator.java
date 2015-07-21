package ee.esutoniagodesu.util.jasperreports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DynamicGenerator implements Serializable {

    private static final long serialVersionUID = 1843376593389644155L;
    private static final int ROW_HEIGHT = 16;
    private static final int COLUMN_WIDTH = 150;

    /**
     * Method to generate dynamically JasperReport Design.
     */
    public static JasperDesign getJasperDesign(String sheetName, List<DynamicParameter> parameters, Map<String, String> titleHeaderMap) throws JRException {
        //JasperDesign
        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName(sheetName);


        JRDesignBand titleBand = new JRDesignBand();

        JRDesignBand pHeaderBand = new JRDesignBand();

        //columns
        JRDesignBand cDetailBand = new JRDesignBand();
        JRDesignBand cHeaderBand = new JRDesignBand();
        JRDesignBand cFooterBand = new JRDesignBand();

        JRDesignBand pFooterBand = new JRDesignBand();
        JRDesignBand summaryBand = new JRDesignBand();

        JRDesignStyle boldStyle = new JRDesignStyle();
        boldStyle.setBold(true);


        cHeaderBand.setSplitType(SplitTypeEnum.STRETCH);
        pHeaderBand.setSplitType(SplitTypeEnum.STRETCH);

        titleBand.setHeight(ROW_HEIGHT);
        pHeaderBand.setHeight(ROW_HEIGHT * titleHeaderMap.size());
        cHeaderBand.setHeight(ROW_HEIGHT);
        cDetailBand.setHeight(ROW_HEIGHT);


        int thCount = 0;
        for (Map.Entry<String, String> entry : titleHeaderMap.entrySet()) {
            //columns heade
            JRDesignTextField tf = new JRDesignTextField();
            tf.setX(0);
            tf.setY(thCount * ROW_HEIGHT);
            tf.setBold(true);
            tf.setHeight(ROW_HEIGHT);
            tf.setWidth(COLUMN_WIDTH);
            tf.setStretchWithOverflow(true);

            tf.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
            tf.setExpression(new JRDesignExpression("\"" + entry.getKey() + "\""));
            pHeaderBand.addElement(tf);

            if (entry.getValue() != null) {
                tf = new JRDesignTextField();
                tf.setX(COLUMN_WIDTH);
                tf.setY(thCount * ROW_HEIGHT);
                tf.setHeight(ROW_HEIGHT);
                tf.setWidth(COLUMN_WIDTH);
                tf.setStretchWithOverflow(true);

                tf.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
                tf.setExpression(new JRDesignExpression("\"" + entry.getValue() + "\""));
                pHeaderBand.addElement(tf);
            }
            thCount++;
        }


        int count = 0;
        for (DynamicParameter dp : parameters) {
            //columns header
            JRDesignTextField tfHeader = new JRDesignTextField();
            tfHeader.setX(count * COLUMN_WIDTH);
            tfHeader.setY(0);
            tfHeader.setBold(true);
            tfHeader.setStretchWithOverflow(true);
            tfHeader.setHeight(ROW_HEIGHT);
            tfHeader.setWidth(COLUMN_WIDTH);

            tfHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
            tfHeader.setExpression(new JRDesignExpression("\"" + dp.getHeaderName() + "\""));
            cHeaderBand.addElement(tfHeader);
            int c = 0;
            int fc = dp.getColumnFields().length;
            int cw = COLUMN_WIDTH / fc;
            for (String s : dp.getColumnFields()) {
                JRDesignField field = new JRDesignField();
                field.setName(s);
                field.setValueClassName(dp.getColumnClass());
                jasperDesign.addField(field);
                JRDesignTextField textField = new JRDesignTextField();
                textField.setX(count * COLUMN_WIDTH + c * cw);
                textField.setY(0);
                textField.setWidth(cw);
                textField.setHeight(ROW_HEIGHT);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
                textField.setExpression(new JRDesignExpression("$F{" + s + "}"));
                textField.setBlankWhenNull(true);
                textField.setStretchWithOverflow(true);
                cDetailBand.addElement(textField);
                c++;
            }

            count++;
        }

        jasperDesign.setTitle(titleBand);
        jasperDesign.setPageHeader(pHeaderBand);
        jasperDesign.setColumnHeader(cHeaderBand);
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(cDetailBand);
        jasperDesign.setColumnFooter(cFooterBand);
        jasperDesign.setPageFooter(pFooterBand);
        jasperDesign.setSummary(summaryBand);

        return jasperDesign;
    }
}
