
package com.bizvision.dpf.processor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processorPerformence complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processorPerformence">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cpuUsage" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="memoryAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processorPerformence", propOrder = {
    "cpuUsage",
    "memoryAmount"
})
public abstract class ProcessorPerformence {

    protected float cpuUsage;
    protected float memoryAmount;

    /**
     * Gets the value of the cpuUsage property.
     * 
     */
    public float getCpuUsage() {
        return cpuUsage;
    }

    /**
     * Sets the value of the cpuUsage property.
     * 
     */
    public void setCpuUsage(float value) {
        this.cpuUsage = value;
    }

    /**
     * Gets the value of the memoryAmount property.
     * 
     */
    public float getMemoryAmount() {
        return memoryAmount;
    }

    /**
     * Sets the value of the memoryAmount property.
     * 
     */
    public void setMemoryAmount(float value) {
        this.memoryAmount = value;
    }

}
