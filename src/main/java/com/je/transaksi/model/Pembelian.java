/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaksi.model;

import com.je.masterdata.model.Barang;
import com.je.masterdata.model.Customer;
import com.je.masterdata.model.Supplier;
import java.sql.Timestamp;

/**
 *
 * @author Newjar
 */
public class Pembelian {
    private String kode;
    private Supplier supplier;
    private Barang barang;
    private Timestamp tgl_pembelian;
    private Integer jumlah;
    private Integer harga_barang;
    private Integer sub_total_harga;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Timestamp getTgl_pembelian() {
        return tgl_pembelian;
    }

    public void setTgl_pembelian(Timestamp tgl_pembelian) {
        this.tgl_pembelian = tgl_pembelian;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(Integer harga_barang) {
        this.harga_barang = harga_barang;
    }

    public Integer getSub_total_harga() {
        return sub_total_harga;
    }

    public void setSub_total_harga(Integer sub_total_harga) {
        this.sub_total_harga = sub_total_harga;
    }

    
}
