/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cloud.skipper.server.repository;

import java.util.List;

import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.skipper.ReleaseNotFoundException;
import org.springframework.cloud.skipper.domain.Info;
import org.springframework.cloud.skipper.domain.Package;
import org.springframework.cloud.skipper.domain.PackageMetadata;
import org.springframework.cloud.skipper.domain.Release;
import org.springframework.cloud.skipper.domain.Status;
import org.springframework.cloud.skipper.domain.StatusCode;
import org.springframework.cloud.skipper.server.AbstractIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

/**
 * @author Ilayaperumal Gopinathan
 */
@ActiveProfiles("repo-test")
@Transactional
public class ReleaseRepositoryTests extends AbstractIntegrationTest {

	@Autowired
	private ReleaseRepository releaseRepository;

	@Test
	public void verifyFindByMethods() {
		PackageMetadata packageMetadata1 = new PackageMetadata();
		packageMetadata1.setName("package1");
		packageMetadata1.setVersion("1.0.0");
		Package pkg1 = new Package();
		pkg1.setMetadata(packageMetadata1);

		PackageMetadata packageMetadata2 = new PackageMetadata();
		packageMetadata2.setName("package2");
		packageMetadata2.setVersion("1.0.1");
		Package pkg2 = new Package();
		pkg2.setMetadata(packageMetadata2);

		Info deletedInfo = new Info();
		Status deletedStatus = new Status();
		deletedStatus.setPlatformStatus("Deleted successfully");
		deletedStatus.setStatusCode(StatusCode.DELETED);
		deletedInfo.setStatus(deletedStatus);

		Info deployedInfo = new Info();
		Status deployedStatus = new Status();
		deployedStatus.setPlatformStatus("Deployed successfully");
		deployedStatus.setStatusCode(StatusCode.DEPLOYED);
		deployedInfo.setStatus(deployedStatus);

		Info unknownInfo = new Info();
		Status unknownStatus = new Status();
		unknownStatus.setPlatformStatus("Unknown");
		unknownStatus.setStatusCode(StatusCode.UNKNOWN);
		unknownInfo.setStatus(unknownStatus);

		Info failedInfo = new Info();
		Status failedStatus = new Status();
		failedStatus.setPlatformStatus("Deployment failed");
		failedStatus.setStatusCode(StatusCode.FAILED);
		failedInfo.setStatus(failedStatus);

		Release release1 = new Release();
		release1.setName("stableA");
		release1.setVersion(1);
		release1.setPlatformName("platform1");
		release1.setPkg(pkg1);
		release1.setInfo(deletedInfo);
		this.releaseRepository.save(release1);

		Release release2 = new Release();
		release2.setName(release1.getName());
		release2.setVersion(2);
		release2.setPlatformName(release1.getPlatformName());
		release2.setPkg(pkg2);
		release2.setInfo(deletedInfo);
		this.releaseRepository.save(release2);

		Release release3 = new Release();
		release3.setName(release1.getName());
		release3.setVersion(3);
		release3.setPlatformName(release1.getPlatformName());
		release2.setPkg(pkg1);
		release3.setInfo(deployedInfo);
		this.releaseRepository.save(release3);

		Release release4 = new Release();
		release4.setName("stableB");
		release4.setVersion(1);
		release4.setPlatformName("platform2");
		release4.setPkg(pkg1);
		release4.setInfo(deletedInfo);
		this.releaseRepository.save(release4);

		Release release5 = new Release();
		release5.setName(release4.getName());
		release5.setVersion(2);
		release5.setPlatformName(release4.getPlatformName());
		release5.setPkg(pkg2);
		release5.setInfo(failedInfo);
		this.releaseRepository.save(release5);

		Release release6 = new Release();
		release6.setName("multipleDeleted");
		release6.setVersion(1);
		release6.setPlatformName("platform2");
		release6.setPkg(pkg1);
		release6.setInfo(deployedInfo);
		this.releaseRepository.save(release6);

		Release release7 = new Release();
		release7.setName(release6.getName());
		release7.setVersion(2);
		release7.setPlatformName(release6.getPlatformName());
		release7.setPkg(pkg2);
		release7.setInfo(deletedInfo);
		this.releaseRepository.save(release7);

		Release release8 = new Release();
		release8.setName(release6.getName());
		release8.setVersion(3);
		release8.setPlatformName(release6.getPlatformName());
		release8.setPkg(pkg2);
		release8.setInfo(failedInfo);
		this.releaseRepository.save(release8);

		Release release9 = new Release();
		release9.setName(release6.getName());
		release9.setVersion(4);
		release9.setPlatformName(release6.getPlatformName());
		release9.setPkg(pkg2);
		release9.setInfo(deletedInfo);
		this.releaseRepository.save(release9);

		Release release10 = new Release();
		release10.setName("multipleRevisions1");
		release10.setVersion(1);
		release10.setPlatformName("platform2");
		release10.setPkg(pkg1);
		release10.setInfo(deployedInfo);
		this.releaseRepository.save(release10);

		Release release11 = new Release();
		release11.setName(release10.getName());
		release11.setVersion(2);
		release11.setPlatformName(release10.getPlatformName());
		release11.setPkg(pkg2);
		release11.setInfo(failedInfo);
		this.releaseRepository.save(release11);

		Release release12 = new Release();
		release12.setName(release10.getName());
		release12.setVersion(3);
		release12.setPlatformName(release10.getPlatformName());
		release12.setPkg(pkg2);
		release12.setInfo(failedInfo);
		this.releaseRepository.save(release12);

		Release release13 = new Release();
		release13.setName("multipleRevisions2");
		release13.setVersion(1);
		release13.setPlatformName("platform2");
		release13.setPkg(pkg1);
		release13.setInfo(deployedInfo);
		this.releaseRepository.save(release13);

		Release release14 = new Release();
		release14.setName(release13.getName());
		release14.setVersion(2);
		release14.setPlatformName(release13.getPlatformName());
		release14.setPkg(pkg2);
		release14.setInfo(deletedInfo);
		this.releaseRepository.save(release14);

		Release release15 = new Release();
		release15.setName(release13.getName());
		release15.setVersion(3);
		release15.setPlatformName(release13.getPlatformName());
		release15.setPkg(pkg2);
		release15.setInfo(unknownInfo);
		this.releaseRepository.save(release15);

		Release release16 = new Release();
		release16.setName("multipleRevisions3");
		release16.setVersion(1);
		release16.setPlatformName(release16.getPlatformName());
		release16.setPkg(pkg2);
		release16.setInfo(failedInfo);
		this.releaseRepository.save(release16);

		Release release17 = new Release();
		release17.setName(release16.getName());
		release17.setVersion(2);
		release17.setPlatformName(release16.getPlatformName());
		release17.setPkg(pkg2);
		release17.setInfo(unknownInfo);
		this.releaseRepository.save(release17);

		// findAll
		Iterable<Release> releases = this.releaseRepository.findAll();
		assertThat(releases).isNotEmpty();
		assertThat(releases).hasSize(17);

		// findByNameAndVersionOrderByApiVersionDesc
		Release foundByNameAndVersion = this.releaseRepository.findByNameAndVersion(release1.getName(), 2);
		assertThat(foundByNameAndVersion).isNotNull();
		assertThat(foundByNameAndVersion.getInfo().getStatus().getStatusCode()).isEqualTo(release2.getInfo().getStatus()
				.getStatusCode());
		assertThat(foundByNameAndVersion.getInfo().getStatus().getPlatformStatus()).isEqualTo(release2.getInfo()
				.getStatus().getPlatformStatus());
		assertThat(foundByNameAndVersion.getPkg().getMetadata().getVersion()).isEqualTo(release2.getPkg().getMetadata()
				.getVersion());
		assertThat(foundByNameAndVersion.getPkg().getMetadata().getName()).isEqualTo(release2.getPkg().getMetadata()
				.getName());

		// findLatestRelease
		Release latestRelease = this.releaseRepository.findLatestRelease(release1.getName());
		assertThat(latestRelease).isNotNull();
		assertThat(latestRelease.getName()).isEqualTo(release3.getName());
		assertThat(latestRelease.getVersion()).isEqualTo(release3.getVersion());
		assertThat(latestRelease.getInfo().getStatus().getStatusCode())
				.isEqualTo(release3.getInfo().getStatus().getStatusCode());

		// findReleaseRevisions
		List<Release> releaseRevisions = this.releaseRepository.findReleaseRevisions(release1.getName(), 2);
		assertThat(releaseRevisions).isNotEmpty();
		assertThat(releaseRevisions).hasSize(2);
		assertThat(releaseRevisions.get(0).getName()).isEqualTo(release3.getName());
		assertThat(releaseRevisions.get(0).getVersion()).isEqualTo(release3.getVersion());
		assertThat(releaseRevisions.get(0).getInfo().getStatus().getStatusCode())
				.isEqualTo(release3.getInfo().getStatus().getStatusCode());
		assertThat(releaseRevisions.get(1).getName()).isEqualTo(release2.getName());
		assertThat(releaseRevisions.get(1).getVersion()).isEqualTo(release2.getVersion());
		assertThat(releaseRevisions.get(1).getInfo().getStatus().getStatusCode()).isEqualTo(release2.getInfo()
				.getStatus().getStatusCode());

		// findByNameIgnoreCaseContainingOrderByNameAscVersionDesc
		List<Release> orderByVersion = this.releaseRepository
				.findByNameIgnoreCaseContainingOrderByNameAscVersionDesc(release4.getName());
		assertThat(orderByVersion).isNotEmpty();
		assertThat(orderByVersion).hasSize(2);
		assertThat(orderByVersion.get(0).getName()).isEqualTo(release5.getName());
		assertThat(orderByVersion.get(0).getVersion()).isEqualTo(release5.getVersion());
		assertThat(orderByVersion.get(0).getInfo().getStatus().getStatusCode()).isEqualTo(release5.getInfo()
				.getStatus().getStatusCode());
		assertThat(orderByVersion.get(1).getName()).isEqualTo(release4.getName());
		assertThat(orderByVersion.get(1).getVersion()).isEqualTo(release4.getVersion());
		assertThat(orderByVersion.get(1).getInfo().getStatus().getStatusCode()).isEqualTo(release4.getInfo()
				.getStatus().getStatusCode());

		// findByNameIgnoreCaseContaining
		List<Release> byNameLike = this.releaseRepository.findByNameIgnoreCaseContaining("stable");
		assertThat(byNameLike).isNotEmpty();
		assertThat(byNameLike).hasSize(5);

		// findLatestDeployedOrFailed
		List<Release> deployedOrFailed = this.releaseRepository.findLatestDeployedOrFailed("stable");
		assertThat(deployedOrFailed).isNotEmpty();
		assertThat(deployedOrFailed).hasSize(2);

		List<Release> deployedOrFailedAll = this.releaseRepository.findLatestDeployedOrFailed("");
		assertThat(deployedOrFailedAll).isNotEmpty();
		assertThat(deployedOrFailedAll).hasSize(9);

		Release latestDeletedRelease1 = this.releaseRepository.findLatestReleaseIfDeleted(release1.getName());
		assertThat(latestDeletedRelease1).isNull();

		Release latestDeletedRelease2 = this.releaseRepository.findLatestReleaseIfDeleted(release6.getName());
		assertThat(latestDeletedRelease2.getVersion()).isEqualTo(4);

		// deployed -> failed -> failed
		Release latestReleaseForUpdate1 = this.releaseRepository.findLatestReleaseForUpdate(release10.getName());
		assertThat(latestReleaseForUpdate1.getVersion()).isEqualTo(1);
		// deployed -> deleted -> unknown
		Release latestReleaseForUpdate2 = this.releaseRepository.findLatestReleaseForUpdate(release13.getName());
		assertThat(latestReleaseForUpdate2.getVersion()).isEqualTo(2);

		// deployed -> deleted -> unknown
		Release latestDeployedRelease = this.releaseRepository.findLatestDeployedRelease(release13.getName());
		assertThat(latestDeployedRelease.getVersion()).isEqualTo(1);

		try {
			this.releaseRepository.findLatestDeployedRelease(release4.getName());
			fail("ReleaseNotFoundException is expected");
		}
		catch (ReleaseNotFoundException e) {
			assertThat(e.getMessage())
					.isEqualTo(String.format("Release with the name [%s] doesn't exist", release4.getName()));
		}
		try {
			this.releaseRepository.findLatestReleaseForUpdate(release16.getName());
			fail("ReleaseNotFoundException is expected");
		}
		catch (ReleaseNotFoundException e) {
			assertThat(e.getMessage())
					.isEqualTo(String.format("Release with the name [%s] doesn't exist", release16.getName()));
		}
	}

	@Test
	public void verifyReleaseNotFoundByName() {
		String releaseName = "random";
		try {
			this.releaseRepository.findLatestRelease(releaseName);
			fail("Expected ReleaseNotFoundException");
		}
		catch (ReleaseNotFoundException e) {
			assertThat(e.getMessage().equals(String.format("Release with the name [%s] doesn't exist", releaseName)));
		}
	}

	@Test
	public void verifyReleaseNotFoundByNameAndVersion() {
		String releaseName = "random";
		int version = 1;
		try {
			this.releaseRepository.findByNameAndVersion(releaseName, version);
			fail("Expected ReleaseNotFoundException");
		}
		catch (ReleaseNotFoundException e) {
			assertThat(e.getMessage().equals(
					String.format("Release with the name [%s] and version [%s] doesn't exist", releaseName, version)));
		}
	}
}
