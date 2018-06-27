(function() {

    var app = angular.module('je-transaksi-penjualan-controller', [
        'je-transaksi-penjualan-service',
        'je-masterdata-barang-service',
        'je-masterdata-customer-service',
        'ui.bootstrap', 'dialogs'
    ]);

    app.controller('PenjualanListController', ['$scope', '$location',
        '$dialogs', 'PenjualanListFactory', 'PenjualanDeleteFactory',
        function($scope, $location, $dialogs, PenjualanListFactory, PenjualanDeleteFactory) {

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
                $scope.gridPromise = PenjualanListFactory.query({
                    activePage: newPage,
                    order: $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
                }, function(data) {
                    $scope.penjualanList = data.list;
                    notif($dialogs, data.status, 'List');
                    $scope.totalPenjualan = data.total;
                });
            };
            $scope.pageChanged(1);

            $scope.search = function() {
                $scope.currentPage = 1;
                $scope.pageChanged(1);
            };

            $scope.create = function() {
                $location.path('/transaksi/penjualan/create');
            };

            $scope.delete = function(penjualan) {
                dlg = $dialogs.confirm('Konfirmasi', 'Apakah anda ingin hapus NO.Penjualan : ' + penjualan.kode);
                dlg.result.then(function(btn) {
                    PenjualanDeleteFactory.delete({
                        kode: penjualan.kode
                    }, function(data) {
                        notif($dialogs, data.status, 'Hapus');
                        $scope.search();
                    });

                }, function(btn) {
                    //canceled
                });

            };

            $scope.edit = function(penjualan) {
                $location.path('/transaksi/penjualan/' + penjualan.kode + '/edit');
            };

            $scope.detail = function(penjualan) {
                $location.path('/transaksi/penjualan/' + penjualan.kode + '/detail');
            };

        }
    ]);

    app.controller('PenjualanDetailController', [
        '$scope',
        '$routeParams',
        '$location',
        'PenjualanEditFactory',
        function($scope, $routeParams, $location, PenjualanEditFactory) {

            $scope.title = "Info Detail Penjualan";

            PenjualanEditFactory.show({
                kode: $routeParams.kode
            }, function(data) {
                $scope.penjualan = data.penjualan;
            });


            $scope.cancel = function() {
                $location.path('/transaksi/penjualan');
            };

        }
    ]);

    app.controller('PenjualanCreateController', [
        '$scope',
        '$routeParams',
        '$location',
        '$dialogs',
        'PenjualanCreateFactory', 'BarangListFactory', 'CustomerListFactory', 'CustomerEditFactory', 'BarangEditFactory',
        function($scope, $routeParams, $location, $dialogs,
            PenjualanCreateFactory, BarangListFactory, CustomerListFactory, CustomerEditFactory, BarangEditFactory) {

            $scope.title = "Buat Baru Penjualan";
            $scope.isEdit = false;

            // ordering
            $scope.predicate = 'nama';
            $scope.reverse = false;

            $scope.detailCustomer = function(customer) {

                $scope.gridPromise = CustomerEditFactory.show({
                    kode: $scope.penjualan.customer.kode
                }, function(data) {
                    if (data.customer == null) {
                        dlg = $dialogs.notify('Informasi', 'Data Customer Tidak Ada');
                        $scope.isViewCustomer = false;
                    } else {
                        $scope.penjualan.customer.kode = data.customer.kode;
                        $scope.penjualan.customer.nama = data.customer.nama;
                        $scope.penjualan.customer.alamat = data.customer.alamat;
                        $scope.penjualan.customer.no_telp = data.customer.no_telp;
                        $scope.penjualan.customer.email = data.customer.email;
                        
                        CustomerListFactory.query({
                            order: $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
                        }, function(data) {
                            $scope.customerList = data.list;
                        });
                        
                        $scope.isViewCustomer = true;

                    } 
                });                

                $scope.pilihCustomer = function(customer) {
                    CustomerListFactory.query({
                        order: $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
                    }, function(data) {
                        $scope.customerList = data.list;
                    });
                };
            };
            
            $scope.detailBarang = function (barang) {
                $scope.gridPromise  =  BarangEditFactory.show({
                      kode : $scope.penjualan.barang.kode
                }, function(data) {
                    if(data.barang == null){
                        dlg = $dialogs.notify('Informasi', 'Data Barang Tidak Ada');
                        $scope.isViewBarang = false;
                    }else{	
                        $scope.penjualan.barang.kode= data.barang.kode;
                        $scope.penjualan.barang.nama= data.barang.nama;
                        $scope.penjualan.barang.stok= data.barang.stok;
                        $scope.penjualan.barang.satuan= data.barang.satuan;
                        $scope.penjualan.barang.harga_beli= data.barang.harga_beli;
                        $scope.penjualan.barang.harga_jual= data.barang.harga_jual;
                        
                        $scope.penjualan.harga_barang = data.barang.harga_jual;
                        
                        $scope.isViewBarang = true;		    			
                    }	    			
                });
            };
            
            $scope.penjualan = {

            };

            $scope.save = function() {

                PenjualanCreateFactory.create({
                    penjualan: $scope.penjualan
                }, function(data) {
                    notif($dialogs, data.status, 'Simpan');
                    $location.path('/transaksi/penjualan');
                });

            };

            $scope.cancel = function() {
                $location.path('/transaksi/penjualan');
            };
                       
        }
    ]);

    app.controller('PenjualanEditController', [
        '$scope',
        '$routeParams',
        '$location',
        '$dialogs',
        'PenjualanEditFactory',
        function($scope, $routeParams, $location, $dialogs, PenjualanEditFactory) {

            $scope.title = "Ubah Data Penjualan";

            PenjualanEditFactory.show({
                kode: $routeParams.kode
            }, function(data) {
                $scope.penjualan = data.penjualan;
            });

            $scope.save = function() {
                PenjualanEditFactory.update({
                    kode: $scope.penjualan.kode,
                    penjualan: $scope.penjualan
                }, function(data) {
                    notif($dialogs, data.status, 'Simpan');
                    $location.path('/transaksi/penjualan');
                });

            };


            $scope.cancel = function() {
                $location.path('/transaksi/penjualan');
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