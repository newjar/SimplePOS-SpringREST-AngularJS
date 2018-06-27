(function() {

	var services = angular.module('je-masterdata-barang-service', [ 'ngResource' ]);

	
	services.factory('BarangListFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/barang', {}, {
			query : {
				method : 'GET',
				isArray : false
			}
		});

	} ]);
    
        services.factory('BarangCreateFactory', [ '$resource', function($resource) {

                    return $resource('service/masterdata/barang', {}, {
                            create : {
                                    method : 'POST'
                            }
                    });

        } ]);
	
	services.factory('BarangEditFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/barang/:kode', {}, {
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
    
    
        services.factory('BarangDeleteFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/barang/:kode', {}, {
			delete : {
				method : 'DELETE',
				params :{kode: '@kode'}
			}
		});

	} ]);
	
})();