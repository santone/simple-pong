package com.doclerholding.property;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.doclerholding.property.CommandPropertyLoader.CommandXmlProperty.*;
import static com.doclerholding.property.CommandType.*;

@Slf4j
public class CommandPropertyLoader {

    public CommandProperty load(String fileName) {

        log.debug( "Load properties from xml {}", fileName );

        Document doc = retrieveDocument(fileName);
        doc.getDocumentElement().normalize();
        Node reportUrlItem = doc.getElementsByTagName("reportUrl").item(0);
        String reportUrl = reportUrlItem.getTextContent().trim();

        NodeList nList = doc.getElementsByTagName("command");

        IcmpPingProperty pingWithIcmpCommandProperty = null;
        TcpPingProperty pingWithTcpIpProperty = null;
        TraceProperty traceCommandProperty = null;

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                String type = eElement.getAttribute("type");

                if (ICMP_PING.type.equalsIgnoreCase(type)) {
                    log.debug("Read ping command");
                    pingWithIcmpCommandProperty = new IcmpPingProperty(readString(eElement, PING_COMMAND.name),
                            readLong(eElement, DELAY_IN_SEC.name));
                } else if (TCP_PING.type.equalsIgnoreCase(type)) {
                    log.debug("Read tcp command");
                    pingWithTcpIpProperty = new TcpPingProperty(readLong(eElement, DELAY_IN_SEC.name), readLong(eElement,
                            TIMEOUT_IN_SEC.name));
                } else if (TRACE.type.equalsIgnoreCase(type)) {
                    log.debug("Read trace command");
                    traceCommandProperty = new TraceProperty(readString(eElement, TRACE_COMMAND.name),
                            readLong(eElement, DELAY_IN_SEC.name));
                } else {
                    throw new RuntimeException("Unknown type: " + type);
                }

            }


        }

        CommandProperty commandProperties = CommandProperty.builder()
                .reportUrl(reportUrl)
                .icmpPingProperty(pingWithIcmpCommandProperty)
                .tcpPingProperty(pingWithTcpIpProperty)
                .traceProperty(traceCommandProperty)
                .build();

        log.debug( "Properties loaded: {}", commandProperties );

        return commandProperties;
    }

    private static String readString(Element element, String name) {
        try {
            return element.getElementsByTagName(name).item(0).getTextContent().trim();
        } catch (Exception e) {
            throw new RuntimeException("Can not retrieve property: " + name);
        }
    }

    private static long readLong(Element element, String name) {
        String propertyValue = readString(element, name);
        try {
            return Long.parseLong(propertyValue);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can not retrieve property: " + name);
        }
    }

    private Document retrieveDocument(String fileName) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            return dBuilder.parse(getClass().getResourceAsStream("/" + fileName));
        } catch (Exception e) {
            throw new RuntimeException("Can not load file: " + fileName);
        }
    }

    @RequiredArgsConstructor
    enum CommandXmlProperty {
        DELAY_IN_SEC("delayInSec"),
        PING_COMMAND("pingCommand"),
        TRACE_COMMAND("traceCommand"),
        TIMEOUT_IN_SEC("timeoutInSec"),
        ;

        final String name;
    }
}
