(function() {

	var services = angular.module('je-transaksi-pembelian-service', [ 'ngResource' ]);

	
	services.factory('PembelianListFactory', [ '$resource', function($resource) {

		return $resource('service/transaksi/pembelian', {}, {
			query : {
				method : 'GET',
				isArray : false
			}
		});

	} ]);
    
        services.factory('PembelianCreateFactory', [ '$resource', function($resource) {

                    return $resource('service/transaksi/pembelian', {}, {
                            create : {
                                    method : 'POST'
                            }
                    });

        } ]);
	
	services.factory('PembelianEditFactory', [ '$resource', function($resource) {

		return $resource('service/transaksi/pembelian/:kode', {}, {
			show : {
				method : 'GET',
				params: {kode: '@kode'}
			},
			update : {
				method : 'PUT',
				params :{kode: '@kode'}
			}
		});

	} ]);
    
    
        services.factory('PembelianDeleteFactory', [ '$resource', function($resource) {

		return $resource('service/transaksi/pembelian/:kode', {}, {
			delete : {
				method : 'DELETE',
				params :{kode: '@kode'}
			}
		});

	} ]);
	
})();