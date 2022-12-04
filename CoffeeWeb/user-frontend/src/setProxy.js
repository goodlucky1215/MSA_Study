const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/user-service/hello',
        createProxyMiddleware({
            target: 'http://localhost:9600',// 서버 URL or localhost:설정한포트번호
            changeOrigin: true,
        })
    );
};