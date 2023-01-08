const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use( 
        createProxyMiddleware(['/user-service'],
        {
            target: 'http://localhost:7500',// 서버 URL or localhost:설정한포트번호
            changeOrigin: true,
        })
    );

    app.use( 
        createProxyMiddleware(['/order-service'],
        {
            target: 'http://localhost:9601',// 서버 URL or localhost:설정한포트번호
            changeOrigin: true,
        })
    );
};