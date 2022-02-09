<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
 <xsl:attribute-set name="tableBorder">
  <xsl:attribute name="border">solid 0.3mm black</xsl:attribute>
</xsl:attribute-set>
<xsl:template match="orderDetail">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
          <fo:region-body/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simpleA4">
        <fo:flow flow-name="xsl-region-body">
          <fo:block font-size="24pt" text-align="center" font-weight="bold" space-after="5mm">Order Invoice
          </fo:block>
          <fo:block font-size="16pt" text-align="start" font-weight="bold" space-after="5mm">Order ID:<xsl:value-of select="orderId"/>
          </fo:block>
          <fo:block font-size="12pt" text-align="end" font-weight="bold" space-after="5mm">Date Time:<xsl:value-of select="time"/>
          </fo:block>
          <fo:block font-size="14pt">
          <fo:table table-layout="fixed" width="100%" border-collapse="separate">    
            <fo:table-column column-width="3cm"/>
            <fo:table-column column-width="4cm"/>
            <fo:table-column column-width="3cm"/>
            <fo:table-column column-width="3cm"/>
            <fo:table-column column-width="4cm"/>
            <fo:table-header>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Barcode</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Name</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Quantity</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">MRP</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Amt. per product</fo:block>
                </fo:table-cell>
              </fo:table-header>
            <fo:table-body>
              <xsl:apply-templates select="item"/>
            </fo:table-body>
          </fo:table>
          </fo:block> 
          <fo:block font-size="12pt" text-align="end" font-weight="bold" space-after="5mm">Total Amount :<xsl:value-of select="format-number(total,'#.00')"/>
          </fo:block>
        </fo:flow>
      </fo:page-sequence>
     </fo:root>
</xsl:template>

<xsl:template match="item">
    <fo:table-row>   
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="barcode"/>
        </fo:block>
      </fo:table-cell>

      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="name"/>
        </fo:block>
      </fo:table-cell>
     
     <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="quantity"/>
        </fo:block>
      </fo:table-cell>  

     <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="format-number(mrp,'#.00')"/>
        </fo:block>
      </fo:table-cell>

      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
            <xsl:value-of select="format-number(mrp*quantity,'#.00')"/>
        </fo:block>
      </fo:table-cell>

    </fo:table-row>
  </xsl:template>
  

</xsl:stylesheet>