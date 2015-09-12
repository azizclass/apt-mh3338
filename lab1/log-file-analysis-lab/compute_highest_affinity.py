# No need to process files and manipulate strings - we will
# pass in lists (of equal length) that correspond to
# sites views. The first list is the site visited, the second is
# the user who visited the site.

# See the test cases for more details.


def affinity(site1, site2, user_sites):
    return len(filter(lambda u: site1 in u and site2 in u, user_sites))


def highest_affinity(site_list, user_list, time_list):
    sites = sorted(list(set(site_list)))
    users = sorted(list(set(user_list)))
    user_sites = [[site_list[i] for i in range(len(user_list))
                  if user == user_list[i]] for user in users]
    site_pairs = [(sites[i], sites[j]) for i in range(len(sites))
                  for j in range(i + 1, len(sites))]
    affinities = dict(((site[0], site[1]), affinity(site[0], site[1], user_sites))
                      for site in site_pairs)
    return max(affinities.iterkeys(), key=lambda key: affinities[key])
