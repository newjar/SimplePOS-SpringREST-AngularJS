(function() {

    var app = angular.module('je-transaksi-pembelian-controller', [
        'je-transaksi-pembelian-service',
        'je-masterdata-barang-service',
        'je-masterdata-supplier-service',
        'ui.bootstrap', 'dialogs'
    ]);

    app.controller('PembelianListController', ['$scope', '$location',
        '$dialogs', 'PembelianListFactory', 'PembelianDeleteFactory',
        function($scope, $location, $dialogs, PembelianListFactory, PembelianDeleteFactory) {

            $scope.currentPage = 1;
            $scope.totalBarang = 0;
            $scope.pageSize = 10;

            $scope.pagination = {
                current: 1
            };

            //ordering
            $scope.predicate = 'kode';
            $scope.reverse = false;

            $scope.pageChanged = function(newPage) {
                $scope.gridPromise = PembelianListFactory.query({
                    activePage: newPage,
                    order: $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
                }, function(data) {
                    $scope.pembelianList = data.list;
                    notif($dialogs, data.status, 'List');
                    $scope.totalPembelian = data.total;
                });
            };
            $scope.pageChanged(1);

            $scope.search = function() {
                $scope.currentPage = 1;
                $scope.pageChanged(1);
            };

            $scope.create = function() {
                $location.path('/transaksi/pembelian/create');
            };

            $scope.delete = function(pembelian) {
                dlg = $dialogs.confirm('Konfirmasi', 'Apakah anda ingin hapus NO.Pembelian : ' + pembelian.kode);
                dlg.result.then(function(btn) {
                    PembelianDeleteFactory.delete({
                        kode: pembelian.kode
                    }, function(data) {
                        notif($dialogs, data.status, 'Hapus');
                        $scope.search();
                    });

                }, function(btn) {
                    //canceled
                });

            };

            $scope.edit = function(pembelian) {
                $location.path('/transaksi/pembelian/' + pembelian.kode + '/edit');
            };

            $scope.detail = function(pembelian) {
                $location.path('/transaksi/pembelian/' + pembelian.kode + '/detail');
            };

        }
    ]);

    app.controller('PembelianDetailController', [
        '$scope',
        '$routeParams',
        '$location',
        'PembelianEditFactory',
        function($scope, $routeParams, $location, PembelianEditFactory) {

            $scope.title = "Info Detail Pembelian";

            PembelianEditFactory.show({
                kode: $routeParams.kode
            }, function(data) {
                $scope.pembelian = data.pembelian;
            });


            $scope.cancel = function() {
                $location.path('/transaksi/pembelian');
            };

        }
    ]);

    app.controller('PembelianCreateController', [
        '$scope',
        '$routeParams',
        '$location',
        '$dialogs',
        'PembelianCreateFactory', 'BarangListFactory', 'SupplierEditFactory', 'BarangEditFactory',
        function($scope, $routeParams, $location, $dialogs,
            PembelianCreateFactory, BarangListFactory, SupplierEditFactory, BarangEditFactory) {

            $scope.title = "Buat Baru Pembelian";
            $scope.isEdit = false;

            // ordering
            $scope.predicate = 'nama';
            $scope.reverse = false;

            $scope.detailSupplier = function(supplier) {

                $scope.gridPromise = SupplierEditFactory.show({
                    kode: $scope.pembelian.supplier.kode
                }, function(data) {
                    if (data.supplier == null) {
                        dlg = $dialogs.notify('Informasi', 'Data Supplier Tidak Ada');
                        $scope.isViewSupplier = false;
                    } else {
                        $scope.pembelian.supplier.kode = data.supplier.kode;
                        $scope.pembelian.supplier.nama = data.supplier.nama;
                        $scope.pembelian.supplier.alamat = data.supplier.alamat;
                        $scope.pembelian.supplier.no_telp = data.supplier.no_telp;
                        $scope.pembelian.supplier.email = data.supplier.email;

                        BarangListFactory.query({
                            order: $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
                        }, function(data) {
                            $scope.barangList = data.list;
                        });

                        $scope.isViewSupplier = true;

                    }

                });

            };
            
            $scope.detailBarang = function (barang) {
                $scope.gridPromise  =  BarangEditFactory.show({
                      kode : $scope.pembelian.barang.kode
                }, function(data) {
                    if(data.barang == null){
                        dlg = $dialogs.notify('Informasi', 'Data Barang Tidak Ada');
                        $scope.isViewBarang = false;
                    }else{	
                        $scope.pembelian.barang.kode= data.barang.kode;
                        $scope.pembelian.barang.nama= data.barang.nama;
                        $scope.pembelian.barang.stok= data.barang.stok;
                        $scope.pembelian.barang.satuan= data.barang.satuan;
                        $scope.pembelian.barang.harga_beli= data.barang.harga_beli;
                        $scope.pembelian.barang.harga_jual= data.barang.harga_jual;
                        
                        $scope.pembelian.harga_barang = data.barang.harga_jual;
                        
                        $scope.isViewBarang = true;		    			
                    }	    			
                });
            };
            
            $scope.pembelian = {

            };

            $scope.save = function() {

                PembelianCreateFactory.create({
                    pembelian: $scope.pembelian
                }, function(data) {
                    notif($dialogs, data.status, 'Simpan');
                    $location.path('/transaksi/pembelian');
                });

            };

            $scope.cancel = function() {
                $location.path('/transaksi/pembelian');
            };
                       
        }
    ]);

    app.controller('PembelianEditController', [
        '$scope',
        '$routeParams',
        '$location',
        '$dialogs',
        'PembelianEditFactory',
        function($scope, $routeParams, $location, $dialogs, PembelianEditFactory) {

            $scope.title = "Ubah Data Pembelian";

            PembelianEditFactory.show({
                kode: $routeParams.kode
            }, function(data) {
                $scope.pembelian = data.pembelian;
            });

            $scope.save = function() {
                PembelianEditFactory.update({
                    kode: $scope.pembelian.kode,
                    pembelian: $scope.pembelian
                }, function(data) {
                    notif($dialogs, data.status, 'Simpan');
                    $location.path('/transaksi/pembelian');
                });

            };


            $scope.cancel = function() {
                $location.path('/transaksi/pembelian');
            };

        }
    ]);

    function notif($dialogs, status, result) {

        switch (status) {
            case 'OK':
                if (result == 'Simpan') {
                    dlg = $dialogs.notify('Informasi', 'Data Sukses Disimpan');
                } else if (result == 'Hapus') {
                    dlg = $dialogs.notify('Informasi', 'Data Sukses Dihapus');
                }
                break;
            case '500':
                if (result == 'Simpan') {
                    dlg = $dialogs.error('Data Gagal Disimpan');
                } else if (result == 'Hapus') {
                    dlg = $dialogs.error('Data Gagal Dihapus');
                }
                break;
        }
    }


})();