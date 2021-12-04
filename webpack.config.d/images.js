// expose processed resources to webpack
config.resolve.modules.push(
	config.output.path.replace(/distributions$/,"processedResources\\js\\main")
);

// inline pngs as base64 strings
config.module.rules.push({
	test: /\.png$/,
	type: 'asset/inline'
});
