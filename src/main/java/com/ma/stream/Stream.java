package com.ma.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Stream {

  public static void main(String[] args) {
    List<Invoice> invoices = new ArrayList<>();

    List<Invoice> oracleAndTrainingInvoices = new ArrayList<>();
    List<Integer> ids = new ArrayList<>();
    List<Integer> firstFiveIds = new ArrayList<>();
    for (Invoice inv : invoices) {
      if (inv.getCustomer() == Customer.ORACLE) {
        if (inv.getTitle().contains("Training")) {
          oracleAndTrainingInvoices.add(inv);
        }
      }
    }
    Collections.sort(oracleAndTrainingInvoices, Comparator.comparingDouble(Invoice::getAmount));
    for (Invoice inv : oracleAndTrainingInvoices) {
      ids.add(inv.getId());
    }

    for (int i = 0; i < 5; i++) {
      firstFiveIds.add(ids.get(i));
    }
    // ======================================
    java.util.stream.Stream<Invoice> oracleAndTrainingInvoiceStream = invoices.stream()
        .filter(inv -> inv.getCustomer().equals(Customer.ORACLE))
        .filter(inv -> inv.getTitle().contains("Training"));

    java.util.stream.Stream<Invoice> sortedInvoices
        = oracleAndTrainingInvoiceStream.sorted(Comparator.comparingDouble(Invoice::getAmount));

    java.util.stream.Stream<Integer> idss = sortedInvoices.map(Invoice::getId);

    List<Integer> firstFiveIdss
        = invoices.stream()
        .filter(inv -> inv.getCustomer() == Customer.ORACLE)
        .filter(inv -> inv.getTitle().contains("Training"))
        .sorted(Comparator.comparingDouble(Invoice::getAmount))
        .map(Invoice::getId)
        .limit(5)
        .collect(Collectors.toList());
  }
}
