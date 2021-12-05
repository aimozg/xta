// inline pngs as base64 strings
config.module.rules.push({
	test: /\.png$/,
	type: 'asset/inline'
});
