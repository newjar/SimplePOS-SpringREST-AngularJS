(function() {

	var app = angular.module('je-masterdata-barang-controller', [
			'je-masterdata-barang-service',
			'ui.bootstrap', 'dialogs' ]);

	app.controller('BarangListController', [ '$scope', '$location',
			'$dialogs', 'BarangListFactory','BarangDeleteFactory',
			function($scope, $location, $dialogs, BarangListFactory,BarangDeleteFactory) {

				$scope.currentPage = 1;
				$scope.totalBarang = 0;
				$scope.pageSize = 10;

				$scope.pagination = {
					current : 1
				};

				//ordering
				$scope.predicate='nama';
                                $scope.reverse=false;
                                $scope.tbarang = 0;
	            
				$scope.pageChanged = function(newPage) {
					$scope.gridPromise = BarangListFactory.query({
						activePage : newPage,
						order : $scope.predicate + "-" + ($scope.reverse ? "desc" : "asc")
					}, function(data) {
						$scope.barangList = data.list;
						notif($dialogs, data.status, 'List');
						$scope.totalBarang = data.total;
                                                $scope.tbarang++;
					});
				};
				$scope.pageChanged(1);

				$scope.search = function() {
					$scope.currentPage = 1;
					$scope.pageChanged(1);
				};

				$scope.create = function() {
					$location.path('/masterdata/barang/create');
				};
				
			   $scope.delete = function (barang) {
		        	dlg = $dialogs.confirm('Konfirmasi','Apakah anda ingin hapus Barang : ' + barang.nama);
		        	dlg.result.then(function(btn){
		        		BarangDeleteFactory.delete({kode: barang.kode}, function(data){
		        			notif($dialogs, data.status, 'Hapus');
		            		$scope.search();
		    			});
		            	
		            },function(btn){
		              //canceled
		            });
		            
		        };
		        
                        $scope.edit = function (barang) {
                            $location.path('/masterdata/barang/' + barang.kode + '/edit');
		        };
		        
		        $scope.detail = function (barang) {
		            $location.path('/masterdata/barang/' + barang.kode + '/detail');
		        };

			} ]);
                    
        app.controller('BarangDetailController', [
                        '$scope',
                        '$routeParams',
                        '$location',
                        'BarangEditFactory',
                        function($scope, $routeParams, $location, BarangEditFactory) {

		$scope.title = "Info Detail Barang";

		BarangEditFactory.show({
			kode : $routeParams.kode
		}, function(data) {
			$scope.barang = data.barang;
		});


		$scope.cancel = function() {
			$location.path('/masterdata/barang');
		};

	} ]);

        app.controller('BarangCreateController', [
                '$scope',
                '$routeParams',
                '$location',
                '$dialogs',
                'BarangCreateFactory',
                function($scope, $routeParams, $location,$dialogs,
                                BarangCreateFactory) {

                        $scope.title = "Buat Baru Barang";
                        $scope.isEdit = false;

                        $scope.barang = {

                        };
                        $scope.save = function() {
                                BarangCreateFactory.create({
                                        barang : $scope.barang
                                }, function(data) {
                                        notif($dialogs, data.status, 'Simpan');
                                        $location.path('/masterdata/barang');
                                });

                        };

                        $scope.cancel = function() {
                                $location.path('/masterdata/barang');
                        };

                } ]);

	app.controller('BarangEditController', [
                '$scope',
                '$routeParams',
                '$location',
                '$dialogs',
                'BarangEditFactory',
                function($scope, $routeParams, $location, $dialogs,BarangEditFactory) {

                        $scope.title = "Ubah Barang";
                        $scope.isEdit = true;

                        BarangEditFactory.show({
                                kode : $routeParams.kode
                        }, function(data) {
                                $scope.barang = data.barang;
                        });

                        $scope.save = function() {
                                BarangEditFactory.update({
                                        kode : $scope.barang.kode,
                                        barang : $scope.barang
                                }, function(data) {
                                        notif($dialogs, data.status, 'Simpan');
                                        $location.path('/masterdata/barang');
                                });

                        };

                        $scope.cancel = function() {
                                $location.path('/masterdata/barang');
                        };

                } ]);
	
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
